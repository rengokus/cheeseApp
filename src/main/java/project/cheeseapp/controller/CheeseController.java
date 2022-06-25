package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.controller.request.CheeseAddRequest;
import project.cheeseapp.controller.request.CheeseUpdateRequest;
import project.cheeseapp.entity.Cheese;
import project.cheeseapp.entity.RipeMethod;
import project.cheeseapp.service.CheeseService;
import project.cheeseapp.service.RipeMethodService;

@CrossOrigin
@RestController
@RequestMapping("/admin/cheese")
public class CheeseController {

    @Autowired
    private RipeMethodService ripeMethodService;

    @Autowired
    private CheeseService cheeseService;

    @PostMapping("/add")
    public Cheese add(@RequestBody CheeseAddRequest request) {
        RipeMethod ripeMethod = ripeMethodService.createMethodFromRequest(request);
        ripeMethodService.saveMethod(ripeMethod);
        Cheese cheese = cheeseService.createCheeseFromRequest(request);
        cheese = cheeseService.assignMethod(cheese, ripeMethod);
        return cheeseService.saveCheese(cheese);
    }

    @PostMapping("/update")
    public Cheese update(@RequestParam int id, @RequestBody CheeseUpdateRequest request) {
        Cheese cheese = cheeseService.findById(id);
        cheese = cheeseService.updateCheeseFromRequest(cheese, request);
        return cheeseService.saveCheese(cheese);
    }
}
