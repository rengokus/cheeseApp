package project.cheeseapp.controller.request;


import lombok.Data;

@Data
public class RoomAddRequest {

    private int shelvesCount;
    private double shelvesWidth;
    private double shelvesLength;
}
