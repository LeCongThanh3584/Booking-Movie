package com.example.demo.Repositories;

import com.example.demo.Entities.Schedule;
import com.example.demo.Exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByCinemaIdAndStartDate(Integer cinemaId, LocalDate startDate);

    @Query("SELECT DISTINCT s.startTime FROM Schedule s WHERE s.movieId = ?1 AND s.cinemaId = ?2 AND s.startDate = ?3")
    List<LocalTime> getStartTimeByMovieIdAndCinemaIdAndStartDate(Integer movieId, Integer cinemaId, LocalDate startDate);

    Optional<Schedule> getScheduleByMovieIdAndCinemaIdAndStartDateAndStartTimeAndRoomId(Integer movieId, Integer cinemaId,
                                                                                        LocalDate startDate, LocalTime startTime, Integer roomId);
}
