package com.example.demo.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ResetPasswordRequest {

    @NotBlank(message = "Mã xác nhận không được để trống")
    @Size(min = 6, max = 6, message = "Mã xác nhận phải có 6 ký tự")
    private String code;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có tối thiểu 6 ký tự")
    private String newPassword;

    @NotBlank(message = "Nhập lại mật khẩu mới không được bỏ trống")
    @Size(min = 6, message = "Xác nhận mật khẩu phải có tối thiểu 6 ký tự")
    private String confirmPassword;
}
