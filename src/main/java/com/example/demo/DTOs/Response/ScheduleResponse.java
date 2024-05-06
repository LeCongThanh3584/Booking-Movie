package com.example.demo.DTOs.Response;

import com.example.demo.Entities.Cinema;
import com.example.demo.Entities.Movie;
import com.example.demo.Entities.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleResponse {

    private int scheduleId;

    private double price;

    private Movie movie;

    private Room room;

    private Cinema cinema;

    private LocalDate startDate;

    private LocalTime startTime;

}
