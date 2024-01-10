package com.example.bookdemo.DTO;

public class LoginResponse {
    private int userId;
    private int role;
    public LoginResponse(int userId,int role){
        this.role=role;
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    // Constructors, getters, and setters
    // ...
}