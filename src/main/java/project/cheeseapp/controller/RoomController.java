package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.configuration.security.AppUserDetails;
import project.cheeseapp.controller.request.RoomAddRequest;
import project.cheeseapp.model.AppUser;
import project.cheeseapp.model.Room;
import project.cheeseapp.service.AppUserService;
import project.cheeseapp.service.RoomService;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public List<Room> getRooms() {
        AppUser user = appUserService.getCurrentUser();
        List<Room> rooms = roomService.getRoomsOfUser(user);
        for (Room room : rooms) {
            room.checkRipe();
        }
        return roomService.saveRooms(rooms);
    }

    @PostMapping("/delete")
    public String deleteRoom(@RequestParam int id) {
        Room room = roomService.findById(id);
        AppUser user = appUserService.getCurrentUser();
        roomService.deleteRoom(user, room);
        return "OK";
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
