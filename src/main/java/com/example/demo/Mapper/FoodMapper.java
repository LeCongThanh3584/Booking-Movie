package com.example.demo.Mapper;

import com.example.demo.DTOs.Request.FoodRequest;
import com.example.demo.DTOs.Response.FoodResponse;
import com.example.demo.Entities.Food;

public class FoodMapper {

    public static Food mapFromRequestToEntity(FoodRequest request) {
        return new Food(
                request.getFoodId(),
                request.getFoodName(),
                request.getPrice()
        );
    }

    public static FoodResponse mapFromEntityToResponse(Food food) {
        return new FoodResponse(
                food.getFoodId(),
                food.getFoodName(),
                food.getPrice(),
                food.getImage()
        );
    }
}
