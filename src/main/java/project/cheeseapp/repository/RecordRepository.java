package project.cheeseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cheeseapp.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

}
