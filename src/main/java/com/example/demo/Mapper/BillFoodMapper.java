package com.example.demo.Mapper;

import com.example.demo.DTOs.Response.BillFoodResponse;
import com.example.demo.Entities.BillFood;

public class BillFoodMapper {

    public static BillFoodResponse mapFromEntityToResponse(BillFood billFood) {
        return new BillFoodResponse(
                billFood.getFoodId(),
                billFood.getFood().getFoodName(),
                billFood.getFood().getPrice(),
                billFood.getQuantity()
        );
    }
}
