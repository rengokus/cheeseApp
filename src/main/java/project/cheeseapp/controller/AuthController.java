package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.cheeseapp.configuration.security.jwt.JwtProvider;
import project.cheeseapp.controller.request.AuthRequest;
import project.cheeseapp.controller.request.RegistrationRequest;
import project.cheeseapp.controller.response.AuthResponse;
import project.cheeseapp.model.AppUser;
import project.cheeseapp.model.Role;
import project.cheeseapp.service.AppUserService;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest registrationRequest) {
        AppUser appUser = new AppUser();
        appUser.setPassword(registrationRequest.getPassword());
        appUser.setLogin(registrationRequest.getLogin());
        appUserService.saveUser(appUser);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        AppUser appUser = appUserService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(appUser.getLogin());
        if (appUser.getRole() == Role.ROLE_USER)
            return new AuthResponse(token, Role.ROLE_USER);
        else
            return new AuthResponse(token, Role.ROLE_ADMIN);
    }
}
