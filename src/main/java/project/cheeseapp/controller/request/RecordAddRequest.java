package project.cheeseapp.controller.request;

import lombok.Data;

@Data
public class RecordAddRequest {
    private String name;
    private String grade;
    private double radius;
    private int count;
}
