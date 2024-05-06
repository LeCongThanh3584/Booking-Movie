package com.example.demo.Mapper;

import com.example.demo.DTOs.Response.TicketResponse;
import com.example.demo.Entities.Ticket;

public class TicketMapper {

    public static TicketResponse mapFromEntityToResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getTicketId(),
                ticket.getPrice(),
                ticket.getSchedule().getMovie().getMovieName(),
                ticket.getSchedule().getCinema().getCinemaName(),
                ticket.getSchedule().getRoom().getRoomName(),
                ticket.getSeat().getSeatName(),
                ticket.getSchedule().getStartDate(),
                ticket.getSchedule().getStartTime()
        );
    }
}
