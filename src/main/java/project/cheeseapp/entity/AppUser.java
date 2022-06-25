package project.cheeseapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Room> rooms = new ArrayList<>();

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
