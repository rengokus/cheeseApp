package project.cheeseapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private LocalDate ripeningDate;
    private boolean isRipe;
    private double spaceRequired;
    private LocalDate lastMaintain;
    private int count;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private Room room;

    @OneToOne
    @JoinColumn(name = "cheese_id")
    private Cheese cheese;
}
