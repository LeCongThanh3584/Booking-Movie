package com.example.demo.Controller;

import com.example.demo.DTOs.Request.ScheduleRequest;
import com.example.demo.DTOs.Response.ScheduleResponse;
import com.example.demo.Entities.Schedule;
import com.example.demo.Exceptions.InvalidDataException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.ScheduleMapper;
import com.example.demo.Services.Schedule.IScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    @Autowired
    private IScheduleService iScheduleService;

    @GetMapping("/user/get-schedule-for-buy-ticket") //API trả về lịch chiếu mà người dùng đã chọn
    public ResponseEntity<?> getScheduleForBuyTicket(@RequestParam Integer movieId, @RequestParam Integer cinemaId,
                                                     @RequestParam String startDate, @RequestParam String startTime,
                                                     @RequestParam Integer roomId)
    {
        try {
            Schedule scheduleReturn = iScheduleService.getScheduleForBuyTicket(movieId, cinemaId,
                    LocalDate.parse(startDate), LocalTime.parse(startTime), roomId);
            ScheduleResponse scheduleResponseReturn = ScheduleMapper.mapFromEntityToResponse(scheduleReturn);
            return new ResponseEntity<>(scheduleResponseReturn, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-schedule-by-cinemaId") //API lấy lịch chiếu theo phim
    public ResponseEntity<?> getScheduleByCinemaId(@RequestParam("cinemaId") Integer cinemaId,
                                                   @RequestParam(value = "startDate", required = false) String startDate)
    {
        LocalDate dateStart;

        if(startDate == null) {  //Nếu mà startDate không gửi lên thì mặc định sẽ là ngày tháng hiện tại
            dateStart = LocalDate.now();
        } else {
            dateStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        List<ScheduleResponse> scheduleResponseList = iScheduleService.getScheduleByCinemaId(cinemaId, dateStart)
                .stream().map(item -> ScheduleMapper.mapFromEntityToResponse(item))
                .collect(Collectors.toList());

        return new ResponseEntity<>(scheduleResponseList, HttpStatus.OK);
    }

    @GetMapping("/get-start-time") //API lấy giờ chiếu phim
    public ResponseEntity<?> getStartTime(@RequestParam Integer movieId,
                                          @RequestParam Integer cinemaId,
                                          @RequestParam String startDate)
    {
        try {
            List<String> startTimeList = iScheduleService.getListStartTime(movieId, cinemaId, LocalDate.parse(startDate));
            return new ResponseEntity<>(startTimeList, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/add-new-schedule") //API thêm mới lịch chiếu phim
    public ResponseEntity<?> addNewSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest, BindingResult bindingResult) {
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

        Schedule scheduleAddnew = ScheduleMapper.mapFromRequestToEntity(scheduleRequest);
        try {
            String response = iScheduleService.addNewSchedule(scheduleAddnew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidDataException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/admin/update-schedule") //API cập nhật rạp chiếu phim
    public ResponseEntity<?> updateSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest, BindingResult bindingResult) {
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

        Schedule scheduleUpdate = ScheduleMapper.mapFromRequestToEntity(scheduleRequest);
        try {
            String response = iScheduleService.updateSchedule(scheduleUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidDataException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/delete-schedule/{id}") //API xoá rạp chiếu phim
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id) {
        try {
            String response = iScheduleService.deleteSchedule(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
