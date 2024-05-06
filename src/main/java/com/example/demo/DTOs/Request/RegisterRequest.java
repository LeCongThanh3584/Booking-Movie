package com.example.demo.DTOs.Request;

import com.example.demo.Entities.User;
import jakarta.validation.constraints.Email;
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

public class RegisterRequest {

    @NotBlank(message = "Tên người dùng không được bỏ trống")
    private String fullName;

    @NotBlank( message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được bỏ trống")
    @Size(min = 6, message = "Mật khẩu phải có tối thiểu 6 ký tự")
    private String password;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    private String phoneNumber;

    public static User mapFromDTOToEntity(RegisterRequest request) {
        return new User(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber()
        );
    }
}
