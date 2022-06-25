package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.entity.*;
import project.cheeseapp.repository.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public Room saveRoom(Room room) {
        return roomRepo.save(room);
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

    public void deleteRoom(Room room) {
        roomRepo.delete(room);
    }

    public Room actualizeInfo(Room room) {
        List<Record> records = room.getRecords();
        if (records.isEmpty()) {
            room.setOptimalMinHum(0);
            room.setOptimalMaxHum(0);
            room.setOptimalMinTemp(0);
            room.setOptimalMaxTemp(0);
            return room;
        }
        List<RipeMethod> ripeMethods = new ArrayList<>();
        for (Record record : records) {
            ripeMethods.add(record.getCheese().getRipeMethod());
        }
        int optMinTemp, optMaxTemp, optMinHum, optMaxHum;
        List<Integer> minTemps = new ArrayList<>();
        List<Integer> maxTemps = new ArrayList<>();
        List<Integer> minHums = new ArrayList<>();
        List<Integer> maxHums = new ArrayList<>();
        for (RipeMethod ripeMethod : ripeMethods) {
            minTemps.add(ripeMethod.getMinTemp());
            maxTemps.add(ripeMethod.getMaxTemp());
            minHums.add(ripeMethod.getMinHum());
            maxHums.add(ripeMethod.getMaxHum());
        }

        Collections.sort(minTemps);
        Collections.sort(maxTemps);
        Collections.sort(minHums);
        Collections.sort(maxHums);

        optMinTemp = minTemps.get(minTemps.size() - 1);
        optMaxTemp = maxTemps.get(0);
        optMinHum = minHums.get(minHums.size() - 1);
        optMaxHum = maxHums.get(0);

        room.setOptimalMinTemp(optMinTemp);
        room.setOptimalMaxTemp(optMaxTemp);
        room.setOptimalMinHum(optMinHum);
        room.setOptimalMaxHum(optMaxHum);

        return room;
    }

    public Room addRecord(Record record, Room room) {
        RipeMethod ripeMethod = record.getCheese().getRipeMethod();
        if (room.getRecords().isEmpty()) {
            room.setOptimalMinTemp(ripeMethod.getMinTemp());
            room.setOptimalMaxTemp(ripeMethod.getMaxTemp());
            room.setOptimalMinHum(ripeMethod.getMinHum());
            room.setOptimalMaxHum(ripeMethod.getMaxHum());
            room.addRecord(record);
            return room;
        } else if (room.getOptimalMinTemp() > ripeMethod.getMaxTemp() || room.getOptimalMaxTemp() < ripeMethod.getMinTemp()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no intersection");
        } else if (room.getOptimalMinHum() > ripeMethod.getMaxHum() || room.getOptimalMaxHum() < ripeMethod.getMinHum()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no intersection");
        }
        room.addRecord(record);
        room = actualizeInfo(room);
        return room;
    }

    public Room deleteRecord(Record record, Room room) {

        room.deleteRecord(record);
        return actualizeInfo(room);
    }

    public Room findById(int id) {
        Optional<Room> room = roomRepo.findById(id);
        if (room.isPresent()) {
            return room.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such room");
    }

    public Room checkRipe(Room room) {
        List<Record> records = room.getRecords();
        LocalDate dateNow = LocalDate.now();
        for (Record record : records) {
            record.setRipe(record.getMinRipeningDate().isBefore(dateNow));
        }
        room.setRecords(records);
        return room;
    }
}
