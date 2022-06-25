package project.cheeseapp.controller.request;

import lombok.Data;
import project.cheeseapp.entity.CheeseType;

@Data
public class CheeseUpdateRequest {

    private String grade;
    private CheeseType type;
}
