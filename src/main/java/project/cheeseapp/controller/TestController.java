package project.cheeseapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.cheeseapp.controller.response.TestResponse;

@RestController
@CrossOrigin
@RequestMapping
public class TestController {

    @GetMapping("/test")
    public TestResponse getResponse() {
        TestResponse response = new TestResponse();
        response.setText("aboba");
        return response;
    }
}
