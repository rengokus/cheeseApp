package project.cheeseapp.controller.request;

import lombok.Data;

@Data
public class SuitableRoomsRequest {
    private double spaceRequired;
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
}
