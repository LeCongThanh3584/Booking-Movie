package com.example.demo.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SeatResponse {

    private int seatId;

    private String seatName;

    private boolean available;  //Dùng để xác định xem là chỗ ngồi này đã có ai đặt hay chưa, true là còn chỗ, false là hết chỗ
}
