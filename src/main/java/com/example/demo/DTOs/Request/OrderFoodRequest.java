package com.example.demo.DTOs.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrderFoodRequest {

    private Integer foodId;

    private Integer quantity;
}
