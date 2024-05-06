package com.example.demo.DTOs.Request;

import jakarta.validation.constraints.NotNull;
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

public class ScheduleRequest {

    private int scheduleId;  //Nếu để là kiểu Integer,
    // khi không gửi lên là sẽ bị lỗi null, còn nếu để kiểu int là mặc định nó sẽ là 0

    @NotNull(message = "Giá tiền không được để trống")
    private double price;

    @NotNull(message = "Phim id không được để trống")
    private Integer movieId;

    @NotNull(message = "Phòng id không được để trống")
    private Integer roomId;

    @NotNull(message = "Rạp id không được để trống")
    private Integer cinemaId;

    @NotNull(message = "Ngày chiếu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Giờ chiếu không được để trống")
    private LocalTime startTime;
}
