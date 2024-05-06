package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.MovieRequest;
import com.example.demo.DTOs.Response.MovieResponse;
import com.example.demo.Entities.Movie;

public class MovieMapper {
    public static Movie mapFromRequestToEntity(MovieRequest movieRequest) {
        return new Movie(
                movieRequest.getMovieId(),
                movieRequest.getMovieName(),
                movieRequest.getMovieDuration(),
                movieRequest.getPremiereDate(),
                movieRequest.getDirector(),
                movieRequest.getActor(),
                movieRequest.getDescription(),
                movieRequest.getLanguage(),
                movieRequest.getMovieTypeId()
        );
    }

    public static MovieResponse mapFromEntityToResponse(Movie movie) {

        return new MovieResponse(
                movie.getMovieId(),
                movie.getMovieName(),
                movie.getMovieDuration(),
                movie.getPremiereDate(),
                movie.getDirector(),
                movie.getActor(),
                movie.getDescription(),
                movie.getLanguage(),
                movie.getImage(),
                movie.getMovieType()
        );
    }
}
