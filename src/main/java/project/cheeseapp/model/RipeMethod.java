package project.cheeseapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ripemethods")
public class RipeMethod {
    @Id
    @GeneratedValue
    private Integer id;
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int minDays;
    private int maxDays;
}
