package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.RoomRequest;
import com.example.demo.DTOs.Response.RoomResponse;
import com.example.demo.Entities.Room;

public class RoomMapper {
    public static Room mapFromRequestToEntity(RoomRequest request) {
        return new Room(
                request.getRoomId(),
                request.getRoomName(),
                request.getCapacity(),
                request.getCinemaId(),
                request.getDescription(),
                request.getSeatList()
        );
    }

    public static RoomResponse mapFromEntityToResponse(Room room) {
        return new RoomResponse(
                room.getRoomId(),
                room.getRoomName(),
                room.getCapacity(),
                room.getDescription()
        );
    }
}
