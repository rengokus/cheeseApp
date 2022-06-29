package project.cheeseapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int currTemp;
    private int currHum;
    private int shelvesCount;
    private double shelfWidth;
    private double shelfLength;
    private double freeSpace;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    @JsonManagedReference
    private List<Record> records;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private AppUser appUser;

    public Room(int shelvesCount, double shelfWidth, double shelfLength, AppUser appUser) {
        this.shelvesCount = shelvesCount;
        this.shelfWidth = shelfWidth;
        this.shelfLength = shelfLength;
        this.appUser = appUser;
        this.freeSpace = shelfLength * shelfWidth * shelvesCount;
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
            record.setRipe(record.getRipeningDate().isBefore(dateNow));
        }
        //setRecords(records);
    }
}
