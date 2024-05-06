package com.example.demo.Services.Schedule;

import com.example.demo.Entities.Schedule;
import com.example.demo.Exceptions.InvalidDataException;
import com.example.demo.Exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IScheduleService {
    Schedule getScheduleForBuyTicket(Integer movieId, Integer cinemaId, LocalDate startDate, LocalTime startTime, Integer roomId) throws NotFoundException;
    List<String> getListStartTime(Integer movieId, Integer cinemaId, LocalDate startDate) throws NotFoundException;
    List<Schedule> getScheduleByCinemaId(Integer cinemaId, LocalDate startDate);
    String addNewSchedule(Schedule scheduleAddnew) throws NotFoundException, InvalidDataException;
    String updateSchedule(Schedule scheduleUpdate) throws NotFoundException, InvalidDataException;
    String deleteSchedule(Integer scheduleId) throws NotFoundException;
}
