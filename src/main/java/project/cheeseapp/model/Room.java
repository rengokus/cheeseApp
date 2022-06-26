package project.cheeseapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue
    private Integer id;
    private int optimalMinTemp;
    private int optimalMaxTemp;
    private int optimalMinHum;
    private int optimalMaxHum;
    private int shelvesCount;
    private int shelfWidth;
    private int shelfLength;

    @OneToMany(mappedBy = "room")
    private List<Record> records;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private AppUser appUser;

    public Room(int shelvesCount, int shelfWidth, int shelfLength, AppUser appUser) {
        this.shelvesCount = shelvesCount;
        this.shelfWidth = shelfWidth;
        this.shelfLength = shelfLength;
        this.appUser = appUser;
    }

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
