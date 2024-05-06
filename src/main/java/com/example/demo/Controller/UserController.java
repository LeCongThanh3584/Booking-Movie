package com.example.demo.Controller;

import com.example.demo.DTOs.Request.ChangePasswordRequest;
import com.example.demo.DTOs.Request.UserRequest;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Services.User.IUserService;
import com.example.demo.Utils.GetUserLoginUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/user/update-infor") //API cập nhật thông tin cá nhân của người dùng
    public ResponseEntity<?> updateInforUser(@RequestBody @Valid UserRequest userRequest, BindingResult bindingResult) {
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

        User userLogin = GetUserLoginUtil.getUserLogin();

        try {
            String response = iUserService.updateInfor(userLogin.getUserId(), userRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/user/change-password") //API thay đổi mật khẩu khi đăng nhập
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request, BindingResult bindingResult) {
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

        User userLogin = GetUserLoginUtil.getUserLogin();

        try {
            String response = iUserService.changePassword(userLogin.getUserId(), request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
