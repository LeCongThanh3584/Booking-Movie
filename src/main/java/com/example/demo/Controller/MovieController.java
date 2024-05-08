package com.example.demo.Controller;

import com.example.demo.DTOs.Request.MovieRequest;
import com.example.demo.DTOs.Response.MovieResponse;
import com.example.demo.DTOs.Response.PaginationResponse;
import com.example.demo.Entities.Movie;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.MovieMapper;
import com.example.demo.Services.Movie.IMovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private IMovieService iMovieService;

    @GetMapping("/get-all-movie") //API Lấy tất cả movie mới nhất
    public ResponseEntity<?> getAllMovie(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<Movie> moviePage = iMovieService.getAllMovieAndPagination(page);

        List<MovieResponse> movieResponseList = moviePage.stream().map(
                item -> MovieMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        PaginationResponse response = new PaginationResponse<>(
                moviePage.getTotalElements(), moviePage.getTotalPages(),
                moviePage.getPageable().getPageNumber(), moviePage.getPageable().getPageSize(),
                movieResponseList
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-movie-by-id")  //API chi tiết từng movie
    public ResponseEntity<?> getMovieById(@RequestParam("movieId") @Min(1) Integer movieId) {
        try {
            Movie movieReturn = iMovieService.getMovieById(movieId);
            MovieResponse movieResponseReturn = MovieMapper.mapFromEntityToResponse(movieReturn);
            return new ResponseEntity<>(movieResponseReturn, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search-movie") //API Tìm kiếm movie
    public ResponseEntity<?> searchMovie(@RequestParam(value = "keyword", required = false) String keyword) {
        List<MovieResponse> movieResponseList = iMovieService.searchMovie(keyword).stream().map(
                item -> MovieMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        return new ResponseEntity<>(movieResponseList, HttpStatus.OK);
    }


    @PostMapping("/admin/add-new-movie") //API thêm mới movie
    public ResponseEntity<?> addNewMovie(@ModelAttribute @Valid MovieRequest movieRequest,
                                         @RequestParam("movieImage") MultipartFile image,
                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return new ResponseEntity<>(errorMsg, HttpStatus.OK);
        }

        Movie movieAddNew = MovieMapper.mapFromRequestToEntity(movieRequest);

        try {
            String response = iMovieService.addNewMovie(movieAddNew, image);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/update-movie") //API Cập nhật movie
    public ResponseEntity<?> updateMovie(@ModelAttribute @Valid MovieRequest movieRequest,
                                         @RequestParam(value = "movieImage", required = false) MultipartFile image,
                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return new ResponseEntity<>(errorMsg, HttpStatus.OK);
        }

        Movie movieUpdate = MovieMapper.mapFromRequestToEntity(movieRequest);
        try {
            String response = iMovieService.updateMovie(movieUpdate, image);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/admin/delete-movie/{id}")  //API xoá movie
    public ResponseEntity<?> deleteMovie(@PathVariable @Min(1) Integer id){
        try {
            String response = iMovieService.deleteMovie(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
