package com.example.demo.Services.User;

import com.example.demo.DTOs.Request.ChangePasswordRequest;
import com.example.demo.DTOs.Request.ResetPasswordRequest;
import com.example.demo.DTOs.Request.UserRequest;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.ExistException;
import com.example.demo.Exceptions.NotFoundException;

public interface IUserService {
    String register(User user) throws NotFoundException, ExistException;
    String activeAccount(String code);
    String sendCodeToEmail(String email) throws NotFoundException;
    String resetPassword(ResetPasswordRequest request) throws Exception;
    String updateInfor(Integer userId, UserRequest request) throws NotFoundException;
    String changePassword(Integer userId, ChangePasswordRequest request) throws Exception;
}
