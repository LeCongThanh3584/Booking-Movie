package com.example.demo.Controller;

import com.example.demo.DTOs.Request.MovieTypeRequest;
import com.example.demo.DTOs.Response.MovieTypeResponse;
import com.example.demo.Entities.MovieType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.MovieTypeMapper;
import com.example.demo.Services.MovieType.IMovieTypeService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MovieTypeController {

    @Autowired
    private IMovieTypeService iMovieTypeService;

    @GetMapping("/get-all-movie-type") //API lấy tất cả thể loại phim
    public ResponseEntity<?> getAllMovieType() {
        List<MovieTypeResponse> movieTypeResponseList = iMovieTypeService.getAllMovieType().stream().map(
                item -> MovieTypeMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        return new ResponseEntity<>(movieTypeResponseList, HttpStatus.OK);
    }

    @PostMapping("/admin/add-new-movie-type") //API thêm mới thể loại phim
    public ResponseEntity<?> addNewMovieType(@RequestBody @Valid MovieTypeRequest request, BindingResult bindingResult) {
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

        MovieType movieTypeAddnew = MovieTypeMapper.mapFromRequestToEntity(request);
        String response = iMovieTypeService.addNewMovieType(movieTypeAddnew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/update-movie-type") //API cập nhật thể loại phim
    public ResponseEntity<?> updateMovieType(@RequestBody @Valid MovieTypeRequest request, BindingResult bindingResult) {
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

        MovieType movieTypeUpdate = MovieTypeMapper.mapFromRequestToEntity(request);
        try {
            String response = iMovieTypeService.updateMovieType(movieTypeUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete-movie-type/{id}") //API xoá thể loại phim
    public ResponseEntity<?> deleteMovieType(@PathVariable Integer id) {
        try {
            String response = iMovieTypeService.deleteMovieType(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
