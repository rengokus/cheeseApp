package project.cheeseapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Integer id;
    private int optimalMinTemp;
    private int optimalMaxTemp;
    private int optimalMinHum;
    private int optimalMaxHum;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private List<Record> records = new ArrayList<>();

    public void addRecord(Record record) {
        records.add(record);
    }

    public void deleteRecord(Record record) {
        records.remove(record);
    }

    public void checkRipe() {
        LocalDate dateNow = LocalDate.now();
        for (Record record : records) {
            record.setRipe(record.getMinRipeningDate().isBefore(dateNow));
        }
        //setRecords(records);
    }
}
