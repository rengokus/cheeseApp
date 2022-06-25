package project.cheeseapp.controller.request;

import lombok.Data;
import project.cheeseapp.entity.CheeseType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CheeseAddRequest {

    private String grade;
    @Enumerated(EnumType.STRING)
    private CheeseType type;
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int minDays;
    private int maxDays;
}
