package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.controller.request.RoomAddRequest;
import project.cheeseapp.controller.request.SetRoomConditionsRequest;
import project.cheeseapp.controller.request.SuitableRoomsRequest;
import project.cheeseapp.model.AppUser;
import project.cheeseapp.model.Room;
import project.cheeseapp.service.AppUserService;
import project.cheeseapp.service.RecordService;
import project.cheeseapp.service.RoomService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public List<Room> getRooms() {
        AppUser user = appUserService.getCurrentUser();
        List<Room> rooms = roomService.getRoomsOfUser(user);
        rooms.forEach(r -> {
            r.checkRipe();
            recordService.saveRecords(r.getRecords());
            roomService.saveRoom(r);
        });
        return rooms;
//        rooms.forEach(Room::checkRipe);
//        return roomService.saveRooms(rooms);
    }

    @GetMapping("/string")
    public String getString() {
        return "string";
    }

    @PostMapping("/get")
    public Room getRoomById(@RequestParam int roomId) {
        return roomService.getRoomById(roomId);
    }

    @PostMapping
    public Room updateRoomConditions(@RequestParam int roomId) {
        Room room = roomService.getRoomById(roomId);
        roomService.updateConditions(room);
        return room;
    }

    @PostMapping("/suitableRooms")
    public List<Room> getSuitableRooms(@RequestBody SuitableRoomsRequest request) {
        return roomService.getSuitableRooms(request);
    }

    @PostMapping("/delete")
    public String deleteRoom(@RequestParam int roomId) {
        Room room = roomService.getRoomById(roomId);
        AppUser user = appUserService.getCurrentUser();
//        roomService.deleteRoom(user, room);
        roomService.deleteRoom(user, room);
        //AppUser user = appUserService.getCurrentUser();
        //user.getRooms().remove(room);
        //appUserService.saveUser(user);
        return "OK";
    }

    @PostMapping("/{roomId}")
    public Room setConditions(@PathVariable int roomId, @RequestBody SetRoomConditionsRequest request) {
        Room room = roomService.getRoomById(roomId);
        room.setCurrHum(request.getHum());
        room.setCurrTemp(request.getTemp());
        return roomService.saveRoom(room);
    }

    @PostMapping("/add")
    public Room addRoom(@RequestBody RoomAddRequest request) {

        Room room = new Room(
                request.getShelvesCount(),
                request.getShelvesWidth(),
                request.getShelvesLength(),
                appUserService.getCurrentUser()
        );

        return roomService.saveRoom(room);
//        AppUser user = appUserService.getCurrentUser();
//        if (user == null) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//        Room room = new Room();
//        return roomService.addRoom(user, room);
    }


}
