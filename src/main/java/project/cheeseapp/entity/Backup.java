package project.cheeseapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Backup {

    @Id
    @GeneratedValue
    private Integer id;

    private String path;
    private String name;
    private LocalDate date;
}
