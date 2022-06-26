package project.cheeseapp.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.cheeseapp.model.Role;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Role role;
}
