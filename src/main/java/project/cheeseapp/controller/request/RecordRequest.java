package project.cheeseapp.controller.request;

import lombok.Data;
import project.cheeseapp.model.CheeseType;

@Data
public class RecordRequest {
    private String name;
    private CheeseType type;
}
