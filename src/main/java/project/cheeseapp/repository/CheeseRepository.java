package project.cheeseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cheeseapp.entity.Cheese;

@Repository
public interface CheeseRepository extends JpaRepository<Cheese, Integer> {
    Cheese findByGrade(String grade);
}
