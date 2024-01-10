package com.example.bookdemo.DTO;

public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials() {
        // 默认构造函数
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
