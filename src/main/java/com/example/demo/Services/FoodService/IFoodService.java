package com.example.demo.Services.FoodService;

import com.example.demo.Entities.Food;
import com.example.demo.Exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFoodService {
    List<Food> getAllFood();
    String addNewFood(Food newFood, MultipartFile image);
    String updateFood(Food foodUpdate, MultipartFile image) throws NotFoundException;
    String deleteFood(Integer foodId) throws NotFoundException;
}
