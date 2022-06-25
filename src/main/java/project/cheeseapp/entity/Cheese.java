package project.cheeseapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Cheese {

    @Id
    @GeneratedValue
    private Integer id;
    private String grade;
    @Enumerated(EnumType.STRING)
    private CheeseType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "method_id")
    private RipeMethod ripeMethod;
}
