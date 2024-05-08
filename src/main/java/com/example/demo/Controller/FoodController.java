package com.example.demo.Controller;

import com.example.demo.DTOs.Request.FoodRequest;
import com.example.demo.DTOs.Response.FoodResponse;
import com.example.demo.Entities.Food;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.FoodMapper;
import com.example.demo.Services.FoodService.IFoodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FoodController {

    @Autowired
    private IFoodService iFoodService;

    @GetMapping("/user/get-all-food") //API lấy tất cả đồ ăn ra
    public ResponseEntity<?> getAllFood() {
        List<FoodResponse> foodResponseList = iFoodService.getAllFood().stream().map(
                item -> FoodMapper.mapFromEntityToResponse(item)
        ).collect(Collectors.toList());

        return new ResponseEntity<>(foodResponseList, HttpStatus.OK);
    }

    @PostMapping("/admin/add-new-food") //API thêm mới đồ ăn
    public ResponseEntity<?> addNewFood(@ModelAttribute @Valid FoodRequest request,
                                        @RequestParam("imageFood") MultipartFile image,
                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return new ResponseEntity<>(errorMsg, HttpStatus.OK);
        }

        Food newFood = FoodMapper.mapFromRequestToEntity(request);
        String response = iFoodService.addNewFood(newFood, image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/update-food") //API cập nhật đồ ăn
    public ResponseEntity<?> updateFood(@ModelAttribute @Valid FoodRequest request,
                                        @RequestParam(value = "imageFood", required = false) MultipartFile image,
                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errors= new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg= "";

            for(String key: errors.keySet()){
                errorMsg+= "Lỗi ở: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return new ResponseEntity<>(errorMsg, HttpStatus.OK);
        }

        Food foodUpdate = FoodMapper.mapFromRequestToEntity(request);
        try {
            String response = iFoodService.updateFood(foodUpdate, image);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete-food/{id}") //API Xoá đồ ăn
    public ResponseEntity<?> deleteFood(@PathVariable @Min(1) Integer id) {
        try {
            String response = iFoodService.deleteFood(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
