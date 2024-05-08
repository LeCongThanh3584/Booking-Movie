package com.example.demo.Controller;

import com.example.demo.DTOs.Request.RoomRequest;
import com.example.demo.DTOs.Response.RoomResponse;
import com.example.demo.Entities.Room;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.RoomMapper;
import com.example.demo.Services.Room.IRoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private IRoomService iRoomService;

    @GetMapping("/get-room") //API Lấy room theo thông tin lịch chiếu phim
    public ResponseEntity<?> getRoom(@RequestParam Integer movieId, @RequestParam Integer cinemaId,
                                     @RequestParam String startDate, @RequestParam String startTime)
    {
        try {
            List<RoomResponse> roomResponseList = iRoomService.getRoom(movieId, cinemaId, LocalDate.parse(startDate), LocalTime.parse(startTime))
                    .stream().map(item -> RoomMapper.mapFromEntityToResponse(item)).collect(Collectors.toList());
            return new ResponseEntity<>(roomResponseList, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/add-new-room") //API thêm mới rạp chiếu phim
    public ResponseEntity<?> addNewRoom(@RequestBody @Valid RoomRequest request, BindingResult bindingResult) {
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

        Room roomAddNew = RoomMapper.mapFromRequestToEntity(request);
        try {
            String response = iRoomService.addNewRoom(roomAddNew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/update-room") //API cập nhật rạp chiếu phim
    public ResponseEntity<?> updateRoom(@RequestBody @Valid RoomRequest request, BindingResult bindingResult) {
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

        Room roomUpdate = RoomMapper.mapFromRequestToEntity(request);

        try {
            String response = iRoomService.updateRoom(roomUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete-room/{id}") //API xoá rạp chiếu phim
    public ResponseEntity<?> deleteRoom(@PathVariable @Min(1) Integer id) {
        try {
            String response = iRoomService.deleteRoom(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
