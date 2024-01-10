package com.example.bookdemo.service;

import com.example.bookdemo.DTO.LoginResponse;
import com.example.bookdemo.DTO.ManageUserResponse;
import com.example.bookdemo.DTO.RegisterRequest;
import com.example.bookdemo.entity.User;
import com.example.bookdemo.entity.UserAuth;

import java.util.List;

public interface UserService {
    LoginResponse login(String username, String password);
     User registerUser(RegisterRequest registerRequest);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    List<ManageUserResponse> getAllUsers();

    boolean toggleUserBlock(Long id);
}
