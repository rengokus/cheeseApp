package project.cheeseapp.controller.request;

import lombok.Data;

@Data
public class SetRoomConditionsRequest {

    private int temp;
    private int hum;
}
