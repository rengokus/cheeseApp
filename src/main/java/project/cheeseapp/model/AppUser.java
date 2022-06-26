package project.cheeseapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "appusers")
public class AppUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "appUser")
    @JsonBackReference
    public List<Room> rooms;

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void deleteRoom(Room room) {
        rooms.remove(room);
    }

    public boolean hasRoom(Room room) {
        for (Room r : rooms) {
            if (r.getId().equals(room.getId())) {
                return true;
            }
        }
        return false;
    }
}
