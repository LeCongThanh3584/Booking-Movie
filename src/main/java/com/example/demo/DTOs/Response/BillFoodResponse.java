package com.example.demo.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BillFoodResponse {

    private int foodId;

    private String foodName;

    private double price;

    private int quantity;
}
