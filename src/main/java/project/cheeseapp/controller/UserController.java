package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.cheeseapp.service.CheeseService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CheeseService cheeseService;


    @GetMapping("/grades")
    public List<String> getAllGrades() {
        return cheeseService.getAllGrades();
    }
}
