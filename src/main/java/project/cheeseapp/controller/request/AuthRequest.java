package project.cheeseapp.controller.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
