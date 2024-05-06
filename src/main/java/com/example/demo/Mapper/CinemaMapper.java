package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.CinemaRequest;
import com.example.demo.DTOs.Response.CinemaResponse;
import com.example.demo.Entities.Cinema;

public class CinemaMapper {
    public static Cinema mapFromRequestToEntity(CinemaRequest cinemaRequest) {
        return new Cinema(
                cinemaRequest.getCinemaId(),
                cinemaRequest.getCinemaName(),
                cinemaRequest.getAddress(),
                cinemaRequest.getPhoneNumber(),
                cinemaRequest.getRoomList()
        );
    }

    public static CinemaResponse mapFromEntityToResponse(Cinema cinema) {
        return new CinemaResponse(
                cinema.getCinemaId(),
                cinema.getCinemaName(),
                cinema.getAddress(),
                cinema.getPhoneNumber(),
                cinema.getImage()
        );
    }
}
