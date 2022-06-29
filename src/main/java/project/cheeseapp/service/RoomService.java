package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.controller.request.SuitableRoomsRequest;
import project.cheeseapp.exception.NoFreeSpaceException;
import project.cheeseapp.exception.NoIntersectionException;
import project.cheeseapp.exception.RoomNotFoundException;
import project.cheeseapp.model.*;
import project.cheeseapp.repository.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    private static final double ADDITIONAL_SPACE = 2.25;

    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }

    public Room updateConditions(Room room) {
        List<Record> records = room.getRecords();
        int minHum = records.stream()
                .map(Record::getCheese)
                .map(Cheese::getRipeMethod)
                .mapToInt(RipeMethod::getMinHum)
                .max()
                .orElse(0);

        int maxHum = records.stream()
                .map(Record::getCheese)
                .map(Cheese::getRipeMethod)
                .mapToInt(RipeMethod::getMaxHum)
                .min()
                .orElse(0);

        int minTemp = records.stream()
                .map(Record::getCheese)
                .map(Cheese::getRipeMethod)
                .mapToInt(RipeMethod::getMinTemp)
                .max()
                .orElse(0);

        int maxTemp = records.stream()
                .map(Record::getCheese)
                .map(Cheese::getRipeMethod)
                .mapToInt(RipeMethod::getMaxTemp)
                .min()
                .orElse(0);

        room.setMinTemp(minTemp);
        room.setMaxTemp(maxTemp);
        room.setMinHum(minHum);
        room.setMaxHum(maxHum);

        return roomRepo.save(room);
    }

    public void deleteRoomById(int roomId) {
        Room room = roomRepo.findById(roomId).orElse(null);
        if (room == null) {
            throw new RoomNotFoundException("room not found");
        }
        roomRepo.delete(room);

//        return room;
    }

    public List<Room> saveRooms(List<Room> rooms) {
        return roomRepo.saveAll(rooms);
    }

    public void deleteRoom(AppUser user, Room room) {
        if (user.hasRoom(room)) {
            user.deleteRoom(room);
            roomRepo.delete(room);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Room addRoom(AppUser user, Room room) {
        user.addRoom(room);
        return roomRepo.save(room);
    }

    public List<Room> getRoomsOfUser(AppUser user) {
        return user.getRooms();
    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public void deleteRoom(Room room) {
        roomRepo.delete(room);
    }

    public List<Room> getSuitableRooms(SuitableRoomsRequest request) {
        List<Room> suitableRooms = roomRepo.findAll().stream().filter(r -> r.getRecords().isEmpty()).filter(r -> r.getFreeSpace() >= request.getSpaceRequired()).collect(Collectors.toList());
        suitableRooms.addAll(
                roomRepo.findAll().stream()
                        .filter(r -> r.getMaxHum() >= request.getMinHum() && r.getMinHum() <= request.getMaxHum())
                        .filter(r -> r.getMaxTemp() >= request.getMinTemp() && r.getMinTemp() <= request.getMaxTemp())
                        .filter(r -> r.getFreeSpace() >= request.getSpaceRequired())
                        .collect(Collectors.toList())
        );
        return suitableRooms;
    }

    public Record addRecord(Record record, Room room) {
        double spaceRequired = record.getSpaceRequired();
        if (room.getFreeSpace() < spaceRequired) {
            throw new NoFreeSpaceException("no free space");
        }
        RipeMethod ripeMethod = record.getCheese().getRipeMethod();
        if (room.getRecords().isEmpty()) {
            room.setMinTemp(ripeMethod.getMinTemp());
            room.setMaxTemp(ripeMethod.getMaxTemp());
            room.setMinHum(ripeMethod.getMinHum());
            room.setMaxHum(ripeMethod.getMaxHum());
            record.setRoom(room);
            room.getRecords().add(record);
            room.setFreeSpace(room.getFreeSpace() - spaceRequired);
            roomRepo.save(room);
            return record;
        } else if (room.getMinTemp() > ripeMethod.getMaxTemp() || room.getMaxTemp() < ripeMethod.getMinTemp()) {
            throw new NoIntersectionException("no intersection");
        } else if (room.getMinHum() > ripeMethod.getMaxHum() || room.getMaxHum() < ripeMethod.getMinHum()) {
            throw new NoIntersectionException("no intersection");
        }
        record.setRoom(room);
        room.getRecords().add(record);
        room.setFreeSpace(room.getFreeSpace() - spaceRequired);
        updateConditions(room);
        return record;
    }

    public Room deleteRecord(Record record, Room room) {

        room.getRecords().remove(record);
        room.setFreeSpace(room.getFreeSpace() + record.getSpaceRequired());
        return updateConditions(room);
    }

    public Room getRoomById(int id) {
//        Optional<Room> room = roomRepo.findById(id);
//        if (room.isPresent()) {
//            return room.get();
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such room");
        return roomRepo.findById(id).orElse(null);
    }

    public Room checkRipe(Room room) {
        List<Record> records = room.getRecords();
        LocalDate dateNow = LocalDate.now();
        for (Record record : records) {
            record.setRipe(record.getRipeningDate().isBefore(dateNow));
        }
//        records.forEach(r -> r.setRipe(r.getMinRipeningDate().isBefore(dateNow)));
        room.setRecords(records);
        return room;
    }
}
