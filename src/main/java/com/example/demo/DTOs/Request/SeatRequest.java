package com.example.demo.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SeatRequest {

    private int seatId;

    @NotBlank(message = "Tên ghế ngồi không được để trống")
    private String seatName;

    @NotNull(message = "roomId không được để trống")
    private Integer roomId;
}
