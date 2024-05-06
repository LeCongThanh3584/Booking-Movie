package com.example.demo.Repositories;

import com.example.demo.Entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    List<Cinema> findByAddressContaining(String address);
    @Query("SELECT DISTINCT c FROM Cinema c JOIN Schedule s ON c.cinemaId = s.cinemaId WHERE s.movieId = ?1 AND s.startDate >= ?2")
    List<Cinema> findAllByMovieId(Integer movieId, LocalDate now);
}
