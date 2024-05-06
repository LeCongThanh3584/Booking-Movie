package com.example.demo.Repositories;

import com.example.demo.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r JOIN Schedule s ON r.roomId = s.roomId " +
            "WHERE s.movieId = ?1 AND s.cinemaId = ?2 AND s.startDate = ?3 AND s.startTime = ?4")
    List<Room> getListRoom(Integer movieId, Integer cinemaId, LocalDate startDate, LocalTime startTime);
}
