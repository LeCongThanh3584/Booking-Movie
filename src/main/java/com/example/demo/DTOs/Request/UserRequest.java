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

public class UserRequest {

    @NotBlank(message = "Tên người dùng không được bỏ trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    private String phoneNumber;
}
