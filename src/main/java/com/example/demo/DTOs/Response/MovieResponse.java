package com.example.demo.DTOs.Response;

import com.example.demo.Entities.MovieType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieResponse {

    private int movieId;

    private String movieName;

    private int movieDuration;

    private LocalDate premiereDate;

    private String director;

    private String actor;

    private String description;

    private String language;

    private String image;

    private MovieType movieType;

}
