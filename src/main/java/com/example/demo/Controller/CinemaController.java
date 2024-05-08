package com.example.demo.Controller;

import com.example.demo.DTOs.Request.CinemaRequest;
import com.example.demo.DTOs.Response.CinemaResponse;
import com.example.demo.Entities.Cinema;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.CinemaMapper;
import com.example.demo.Services.Cinema.ICinemaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class CinemaController {

    @Autowired
    private ICinemaService iCinemaService;

    @GetMapping("/find-cinema-by-address") //API tìm rạp theo địa chỉ
    public ResponseEntity<?> findCinemaByAddress(@RequestParam(value = "address", required = false) String address) {

        List<CinemaResponse> cinemaResponseList = iCinemaService.findCinemaByAddress(address).stream().map(
                item -> CinemaMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        return new ResponseEntity<>(cinemaResponseList, HttpStatus.OK);
    }

    @GetMapping("/get-cinema-by-id") //API chi tiết rạp theo id
    public ResponseEntity<?> getCinemaById(@RequestParam("cinemaId") @Min(1) Integer cinemaId) {
        try {
            Cinema cinemaReturn = iCinemaService.getCinemaById(cinemaId);
            CinemaResponse cinemaResponseReturn = CinemaMapper.mapFromEntityToResponse(cinemaReturn);
            return new ResponseEntity<>(cinemaResponseReturn, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-cinema-by-movieId") //API lấy ra rạp theo phim
    public ResponseEntity<?> getCinemaByMovieId(@RequestParam("movieId") Integer movieId) {
        List<CinemaResponse> cinemaResponseList = iCinemaService.getCinemaByMovieId(movieId)
                .stream().map(item -> CinemaMapper.mapFromEntityToResponse(item))
                .collect(Collectors.toList());

        return new ResponseEntity<>(cinemaResponseList, HttpStatus.OK);
    }

    @PostMapping("/admin/add-new-cinema") //API thêm mới rạp
    public ResponseEntity<?> addNewCinema(@ModelAttribute @Valid CinemaRequest cinemaRequest,
                                          @RequestParam(value = "imageCinema") MultipartFile image,
                                          BindingResult bindingResult
    ) {

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

        Cinema cinemaAddnew = CinemaMapper.mapFromRequestToEntity(cinemaRequest);
        String response = iCinemaService.addNewCinema(cinemaAddnew,image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/update-cinema") //API cập nhật rạp
    public ResponseEntity<?> updateCinema(@ModelAttribute @Valid CinemaRequest cinemaRequest,
                                          @RequestParam(value = "imageCinema", required = false) MultipartFile image,
                                          BindingResult bindingResult
    ) {
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

        Cinema cinemaUpdate = CinemaMapper.mapFromRequestToEntity(cinemaRequest);

        try {
            String response = iCinemaService.updateCinema(cinemaUpdate, image);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete-cinema/{id}") //API Xoá rạp
    public ResponseEntity<?> deleteCinema(@PathVariable @Min(1) Integer id) {
        try {
            String response = iCinemaService.deleteCinema(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
