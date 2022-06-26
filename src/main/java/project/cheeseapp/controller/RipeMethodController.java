package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.controller.request.MethodUpdateRequest;
import project.cheeseapp.model.RipeMethod;
import project.cheeseapp.service.RipeMethodService;

@CrossOrigin
@RestController
@RequestMapping("/admin/ripemethod")
public class RipeMethodController {

    @Autowired
    private RipeMethodService ripeMethodService;

    @PostMapping("/update")
    public RipeMethod update(@RequestParam int id, @RequestBody MethodUpdateRequest request) {
        RipeMethod ripeMethod = ripeMethodService.findById(id);
        ripeMethod = ripeMethodService.updateMethodFromRequest(ripeMethod, request);
        return ripeMethodService.saveMethod(ripeMethod);
    }
}