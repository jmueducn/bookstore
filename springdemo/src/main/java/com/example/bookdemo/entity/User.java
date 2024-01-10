package com.example.bookdemo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;

    // 构造函数、getter和setter方法等
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Order> orders;
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // getter和setter方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
