package project.cheeseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cheeseapp.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
