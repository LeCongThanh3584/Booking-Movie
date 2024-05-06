package com.example.demo.DTOs.Request;

import com.example.demo.Entities.Seat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RoomRequest {

    private int roomId;

    @NotBlank(message = "Tên phòng chiếu không được để trống")
    private String roomName;

    private int capacity;

    @NotNull(message = "Id Rạp không được để trống")
    private Integer cinemaId;

    private String description;

    private List<Seat> seatList;
}
