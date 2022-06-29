package project.cheeseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.cheeseapp.model.Cheese;
import project.cheeseapp.model.RipeMethod;
import project.cheeseapp.model.Record;
import project.cheeseapp.repository.RecordRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepo;

    public Record createRecord(String name, Cheese cheese, double radius, int count) {
        RipeMethod method = cheese.getRipeMethod();

        Record record = new Record();
        record.setName(name);
        record.setCheese(cheese);
        LocalDate date = LocalDate.now();
        record.setInitialDate(date);
        record.setLastMaintain(date);
        record.setRipe(false);
        record.setRipeningDate(date.plusDays(method.getRipeningDays()));
        record.setCount(count);
        record.setSpaceRequired((Math.pow(radius + 2.25, 2) * Math.PI) * count);

        return record;
    }

    public Record saveRecord(Record record) {
        return recordRepo.save(record);
    }

    public List<Record> checkRipe(List<Record> records) {
        records.forEach(r -> r.setRipe(!LocalDate.now().isBefore(r.getRipeningDate())));
        return recordRepo.saveAll(records);
    }

    public Record getRecordById(Integer id) {
        Optional<Record> record = recordRepo.findById(id);
        if (record.isPresent()) {
            return record.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "record not found");
    }

    public void deleteRecord(Record record) {
        recordRepo.delete(record);
    }
}
