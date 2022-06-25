package project.cheeseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cheeseapp.entity.Backup;

@Repository
public interface BackupRepository extends JpaRepository<Backup, Integer> {
    Backup findByName(String name);
}
