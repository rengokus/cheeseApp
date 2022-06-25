package project.cheeseapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private LocalDate initialDate;
    private LocalDate minRipeningDate;
    private LocalDate maxRipeningDate;
    private boolean isRipe;

    @OneToOne
    @JoinColumn(name = "cheese_id")
    private Cheese cheese;
}
