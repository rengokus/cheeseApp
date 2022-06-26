package project.cheeseapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private LocalDate initialDate;
    private LocalDate minRipeningDate;
    private LocalDate maxRipeningDate;
    private boolean isRipe;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToOne
    @JoinColumn(name = "cheese_id")
    private Cheese cheese;
}
