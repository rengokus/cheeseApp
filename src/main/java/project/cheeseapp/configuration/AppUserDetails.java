package project.cheeseapp.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.cheeseapp.entity.AppUser;

import java.util.Collection;
import java.util.Collections;

@Component
public class AppUserDetails implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static AppUserDetails fromAppUserToAppUserDetails(AppUser appUser) {
        AppUserDetails appUserDetails = new AppUserDetails();
        appUserDetails.login = appUser.getLogin();
        appUserDetails.password = appUser.getPassword();
        appUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole().name()));
        return appUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
