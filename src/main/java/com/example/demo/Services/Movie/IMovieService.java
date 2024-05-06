package com.example.demo.Services.Movie;

import com.example.demo.Entities.Movie;
import com.example.demo.Exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMovieService {
    Page<Movie> getAllMovieAndPagination(Integer page);
    Movie getMovieById(Integer movieId) throws NotFoundException;
    List<Movie> searchMovie(String keyword);
    String addNewMovie(Movie movieAddNew, MultipartFile image) throws NotFoundException;
    String updateMovie(Movie movieUpdate, MultipartFile image) throws NotFoundException;
    String deleteMovie(Integer movieId) throws NotFoundException;
}
