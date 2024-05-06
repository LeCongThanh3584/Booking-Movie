package com.example.demo.Services.MovieType;

import com.example.demo.Entities.MovieType;
import com.example.demo.Exceptions.NotFoundException;

import java.util.List;

public interface IMovieTypeService {
    List<MovieType> getAllMovieType();
    String addNewMovieType(MovieType movieTypeAddnew);
    String updateMovieType(MovieType movieTypeUpdate) throws NotFoundException;
    String deleteMovieType(Integer id) throws NotFoundException;
}
