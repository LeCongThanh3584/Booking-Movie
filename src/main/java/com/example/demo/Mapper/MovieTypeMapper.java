package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.MovieTypeRequest;
import com.example.demo.DTOs.Response.MovieTypeResponse;
import com.example.demo.Entities.MovieType;

public class MovieTypeMapper {
    public static MovieType mapFromRequestToEntity(MovieTypeRequest request) {
        return new MovieType(
                request.getMovieTypeId(),
                request.getMovieTypeName()
        );
    }

    public static MovieTypeResponse mapFromEntityToResponse(MovieType movieType) {
        return new MovieTypeResponse(
                movieType.getMovieTypeId(),
                movieType.getMovieTypeName()
        );
    }
}
