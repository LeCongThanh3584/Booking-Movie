package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.ScheduleRequest;
import com.example.demo.DTOs.Response.ScheduleResponse;
import com.example.demo.Entities.Schedule;

public class ScheduleMapper {
    public static Schedule mapFromRequestToEntity(ScheduleRequest request) {
        return new Schedule(
                request.getScheduleId(),
                request.getPrice(),
                request.getMovieId(),
                request.getRoomId(),
                request.getCinemaId(),
                request.getStartDate(),
                request.getStartTime()
        );
    }

    public static ScheduleResponse mapFromEntityToResponse(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getScheduleId(),
                schedule.getPrice(),
                schedule.getMovie(),
                schedule.getRoom(),
                schedule.getCinema(),
                schedule.getStartDate(),
                schedule.getStartTime()
        );
    }
}
