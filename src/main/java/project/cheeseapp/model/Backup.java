package project.cheeseapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "backups")
public class Backup {

    @Id
    @GeneratedValue
    private Integer id;

    private String path;
    private String name;
    private LocalDate date;
}
