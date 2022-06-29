package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.model.Cheese;
import project.cheeseapp.model.RipeMethod;
import project.cheeseapp.service.CheeseService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CheeseService cheeseService;


    @GetMapping("/ripemethod/{cheeseGrade}")
    public RipeMethod getMethodByCheeseGrade(@PathVariable String cheeseGrade) {
        Cheese cheese = cheeseService.getCheeseByGrade(cheeseGrade);
        return cheese.getRipeMethod();
    }

    @GetMapping("/grades")
    public List<String> getAllGrades() {
        return cheeseService.getAllGrades();
    }
}
