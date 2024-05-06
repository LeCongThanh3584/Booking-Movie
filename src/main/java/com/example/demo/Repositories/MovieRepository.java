package com.example.demo.Repositories;

import com.example.demo.Entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findAllByOrderByMovieIdDesc(Pageable pageable);
    List<Movie> findByMovieNameContaining(String keyword);
}
