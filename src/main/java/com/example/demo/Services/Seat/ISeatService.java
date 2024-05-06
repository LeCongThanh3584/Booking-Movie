package com.example.demo.Services.Seat;

import com.example.demo.DTOs.Response.SeatResponse;
import com.example.demo.Entities.Seat;
import com.example.demo.Exceptions.NotFoundException;

import java.util.List;

public interface ISeatService {
    List<SeatResponse> getSeatsByScheduleId(Integer scheduleId) throws NotFoundException;
    String addNewSeat(Seat newSeat) throws NotFoundException;
    String updateSeat(Seat seatUpdate) throws NotFoundException;
    String deleteSeat(Integer seatId) throws NotFoundException;
}
