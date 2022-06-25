package project.cheeseapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.cheeseapp.entity.AppUser;
import project.cheeseapp.service.AppUserService;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserService.findByLogin(username);
        return AppUserDetails.fromAppUserToAppUserDetails(appUser);
    }
}
