package com.example.demo.Repositories;

import com.example.demo.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByScheduleId(Integer scheduleId);
    Optional<Ticket> findByScheduleIdAndSeatId(Integer scheduleId, Integer seatId);
}
