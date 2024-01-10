package com.example.bookdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="user_auth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "token")
    private String token;
    @Column(name = "role")
    private String role;
    @Column(name="password")
    private  String password;
    // 构造函数、getter和setter方法等

    public UserAuth() {
    }

    public UserAuth(User user, String token, String role) {
        this.user = user;
        this.token = token;
        this.role = role;
    }
    public UserAuth(User user, String token, String role,String password) {
        this.user = user;
        this.token = token;
        this.role = role;
        this.password=password;
    }
    // getter和setter方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
}