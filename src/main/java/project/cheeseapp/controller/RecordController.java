package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.controller.request.RecordAddRequest;
import project.cheeseapp.exception.NoFreeSpaceException;
import project.cheeseapp.exception.NoIntersectionException;
import project.cheeseapp.model.Cheese;
import project.cheeseapp.model.Record;
import project.cheeseapp.model.Room;
import project.cheeseapp.service.CheeseService;
import project.cheeseapp.service.RecordService;
import project.cheeseapp.service.RoomService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user/room/{roomId}/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CheeseService cheeseService;

    @PostMapping("/add")
    public String addRecord(@RequestBody RecordAddRequest request, @PathVariable int roomId) {
        Cheese cheese = cheeseService.getCheeseByGrade(request.getGrade());
        Room room = roomService.getRoomById(roomId);
        Record record = recordService.createRecord(
                request.getName(),
                cheese,
                request.getRadius(),
                request.getCount()
        );
        try {
            roomService.addRecord(record, room);
            recordService.saveRecord(record);
            return "OK";
        } catch (NoFreeSpaceException | NoIntersectionException ex) {
            return ex.getMessage();
        }

//        roomService.updateConditions(room);
//        return (List<Room>) recordService.saveRecord(record);
//        room = roomService.addRecord(record, room);
//        roomService.saveRoom(room);

//        return "OK";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam int recordId, @PathVariable int roomId) {
        Record record = recordService.getRecordById(recordId);
        Room room = roomService.getRoomById(roomId);
        room = roomService.deleteRecord(record, room);
        recordService.deleteRecord(record);
        return "OK";
    }

    @GetMapping
    public List<Record> getRecords(@PathVariable int roomId) {
        Room room = roomService.getRoomById(roomId);
        List<Record> records = room.getRecords();
        return recordService.checkRipe(records);
    }
}
