package com.example.demo.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketResponse {

    private int ticketId;

    private double price;

    private String movieName;

    private String cinemaName;

    private String roomName;

    private String seatName;

    private LocalDate startDate;

    private LocalTime startTime;
}
