package com.example.demo.DTOs.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FoodResponse {

    private int foodId;

    private String foodName;

    private double price;

    private String image;
}
