package com.example.demo.DTOs.Request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BillRequest {

    private int billId;

    @NotNull(message = "Id người đặt vé không được bỏ trống")
    private Integer userId;

    @NotNull(message = "Id lịch chiếu phim không được bỏ trống")
    private Integer scheduleId;

    @NotNull(message = "Danh sách chỗ ngồi không được để trống")
    @NotEmpty(message = "Danh sách chỗ ngồi không được để trống")
    List<Integer> listSeatId;

    List<OrderFoodRequest> foodRequestList;
}
