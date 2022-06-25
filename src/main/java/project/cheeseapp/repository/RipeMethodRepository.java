package project.cheeseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cheeseapp.entity.RipeMethod;

@Repository
public interface RipeMethodRepository extends JpaRepository<RipeMethod, Integer> {
}
