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
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepo;

    public Record createRecord(String name, Cheese cheese) {
        RipeMethod ripeMethod = cheese.getRipeMethod();
        Record record = new Record();
        record.setName(name);
        record.setCheese(cheese);
        LocalDate date = LocalDate.now();
        record.setInitialDate(date);
        record.setRipe(false);
        record.setMinRipeningDate(date.plusDays(ripeMethod.getMinDays()));
        record.setMaxRipeningDate(date.plusDays(ripeMethod.getMaxDays()));
        return record;
    }

    public Record saveRecord(Record record) {
        return recordRepo.save(record);
    }

    public Record findById(Integer id) {
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
