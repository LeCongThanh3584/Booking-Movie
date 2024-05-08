package com.example.demo.Controller;

import com.example.demo.DTOs.Request.SeatRequest;
import com.example.demo.DTOs.Response.SeatResponse;
import com.example.demo.Entities.Seat;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.SeatMapper;
import com.example.demo.Services.Seat.ISeatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SeatController {

    @Autowired
    private ISeatService iSeatService;

    @GetMapping("/user/get-seats-by-scheduleId") //API Lấy ra các ghế ngồi theo lịch chiếu phim
    public ResponseEntity<?> getSeatsByScheduleId(@RequestParam @Min(1) Integer scheduleId) {
        try {
            List<SeatResponse> seatResponseList = iSeatService.getSeatsByScheduleId(scheduleId);
            return new ResponseEntity<>(seatResponseList, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/add-new-seat") //API thêm mới ghế ngồi
    public ResponseEntity<?> addNewSeat(@RequestBody @Valid SeatRequest seatRequest, BindingResult bindingResult) {
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

        Seat seatAddNew = SeatMapper.mapFromRequestToEntity(seatRequest);
        try {
            String response = iSeatService.addNewSeat(seatAddNew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/update-seat") //API cập nhật thông tin ghế ngồi
    public ResponseEntity<?> updateSeat(@RequestBody @Valid SeatRequest seatRequest, BindingResult bindingResult) {
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

        Seat seatUpdate = SeatMapper.mapFromRequestToEntity(seatRequest);
        try {
            String response = iSeatService.updateSeat(seatUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete-seat/{id}") //API xoá ghế
    public ResponseEntity<?> deleteSeat(@PathVariable @Min(1) Integer id) {
        try {
            String response = iSeatService.deleteSeat(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
