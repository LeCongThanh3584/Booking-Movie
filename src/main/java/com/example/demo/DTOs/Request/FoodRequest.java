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

public class FoodRequest {

    private int foodId;

    @NotBlank(message = "Tên đồ ăn không được để trống")
    private String foodName;

    @NotNull(message = "Giá tiền không được để trống")
    private Double price;
}
