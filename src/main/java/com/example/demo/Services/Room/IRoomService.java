package com.example.demo.Services.Room;

import com.example.demo.Entities.Room;
import com.example.demo.Exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IRoomService {
    List<Room> getRoom(Integer movieId, Integer cinemaId, LocalDate startDate, LocalTime startTime) throws NotFoundException;
    String addNewRoom(Room newRoom) throws NotFoundException;
    String updateRoom(Room roomUpdate)throws NotFoundException;
    String deleteRoom(Integer id) throws NotFoundException;

}
