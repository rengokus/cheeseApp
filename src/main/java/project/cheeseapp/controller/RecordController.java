package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.controller.request.RecordAddRequest;
import project.cheeseapp.model.Cheese;
import project.cheeseapp.model.Record;
import project.cheeseapp.model.Room;
import project.cheeseapp.service.AppUserService;
import project.cheeseapp.service.CheeseService;
import project.cheeseapp.service.RecordService;
import project.cheeseapp.service.RoomService;

@CrossOrigin
@RestController
@RequestMapping("/user/room/{roomId}/record")
public class RecordController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CheeseService cheeseService;

    @PostMapping("/add")
    public String addRecord(@RequestBody RecordAddRequest recordAddRequest, @PathVariable int roomId) {
        Cheese cheese = cheeseService.findByGrade(recordAddRequest.getGrade());
        Room room = roomService.findById(roomId);
        Record record = recordService.createRecord(recordAddRequest.getName(), cheese);
        room = roomService.addRecord(record, room);
        roomService.saveRoom(room);
        return "OK";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam int id, @PathVariable int roomId) {
        Record record = recordService.findById(id);
        Room room = roomService.findById(roomId);
        room = roomService.deleteRecord(record, room);
        recordService.deleteRecord(record);
        roomService.saveRoom(room);
        return "OK";
    }
}
