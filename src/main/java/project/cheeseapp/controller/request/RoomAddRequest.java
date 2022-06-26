package project.cheeseapp.controller.request;


import lombok.Data;

@Data
public class RoomAddRequest {

    private int shelvesCount;
    private int shelvesWidth;
    private int shelvesLength;
}
