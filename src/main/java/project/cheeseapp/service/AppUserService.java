package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.cheeseapp.model.AppUser;
import project.cheeseapp.model.Role;
import project.cheeseapp.model.Room;
import project.cheeseapp.repository.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser saveUser(AppUser appUser) {
        appUser.setRole(Role.ROLE_USER);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public AppUser findByLoginAndPassword(String login, String password) {
        AppUser appUser = findByLogin(login);
        if (appUser != null) {
            if (passwordEncoder.matches(password, appUser.getPassword())) {
                return appUser;
            }
        }
        return null;
    }

    public AppUser getCurrentUser() {
        return findByLogin(SecurityContextHolder
                .getContext()
                .getAuthentication().getName());
    }

    public AppUser addRoom(AppUser user, Room room) {
        user.addRoom(room);
        return user;
    }

    public AppUser findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }

    public AppUser deleteRoom(AppUser user, Room room) {
        user.deleteRoom(room);
        return user;
    }
}
