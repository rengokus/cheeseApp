package project.cheeseapp.controller.request;

import lombok.Data;
import project.cheeseapp.model.CheeseType;

@Data
public class CheeseUpdateRequest {

    private String grade;
    private CheeseType type;
}
