package project.cheeseapp.controller.request;

import lombok.Data;

@Data
public class MethodUpdateRequest {

    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int minDays;
    private int maxDays;
}
