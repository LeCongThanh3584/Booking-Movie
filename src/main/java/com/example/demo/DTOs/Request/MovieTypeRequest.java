package com.example.demo.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieTypeRequest {

    private int movieTypeId;

    @NotBlank(message = "Tên phim không được để trống")
    private String movieTypeName;
}
