package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.SeatRequest;
import com.example.demo.DTOs.Response.SeatResponse;
import com.example.demo.Entities.Seat;

public class SeatMapper {
    public static Seat mapFromRequestToEntity(SeatRequest seatRequest) {
        return new Seat(
            seatRequest.getSeatId(),
            seatRequest.getSeatName(),
            seatRequest.getRoomId()
        );
    }

    public static SeatResponse mapFromEntityToResponse(Seat seat) {
        return new SeatResponse(
                seat.getSeatId(),
                seat.getSeatName(),
                true //Mặc định sẽ để là true
        );
    }
}
