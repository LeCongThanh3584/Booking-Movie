package com.example.demo.Controller;

import com.example.demo.DTOs.Request.LoginRequest;
import com.example.demo.DTOs.Request.RegisterRequest;
import com.example.demo.DTOs.Request.ResetPasswordRequest;
import com.example.demo.DTOs.Response.LoginResponse;
import com.example.demo.Entities.User;
import com.example.demo.Enum.EUserStatus;
import com.example.demo.Exceptions.ExistException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Services.User.IUserService;
import com.example.demo.Utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")  //API đăng ký
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) {
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

        try {
            User userRegister = RegisterRequest.mapFromDTOToEntity(request);
            String response = iUserService.register(userRegister);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExistException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/active-account") //API kích hoạt tài khoản khi đăng ký
    public ResponseEntity<?> activeAccount(@RequestParam("code") String code) {
        String response = iUserService.activeAccount(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login") //API đăng nhập
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
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

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();

            if(user.getUserStatus().getUserStatusName().equals(EUserStatus.NOT_ACTIVATED))
                return new ResponseEntity<>("Tài khoản chưa được kích hoạt", HttpStatus.UNAUTHORIZED);

            String accessToken = jwtUtil.generateAccessToken(user);
            LoginResponse loginResponse = new LoginResponse(user.getEmail(), accessToken);

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);

        }catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Sai email hoặc mật khẩu", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/send-code-to-email") //API gửi mã xác nhận về email để đổi MK khi quên MK
    public ResponseEntity<?> sendCodeToEmail(@RequestParam("email") String email) {
        try {
            String responsee = iUserService.sendCodeToEmail(email);
            return new ResponseEntity<>(responsee, HttpStatus.OK);
        }catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/reset-password")  //API đặt lại mật khẩu
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request, BindingResult bindingResult) {
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

        try {
           String response = iUserService.resetPassword(request);
           return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
