package com.example.demo.Repositories;

import com.example.demo.Entities.Movie;
import com.example.demo.Entities.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Integer> {
}
