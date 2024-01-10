package com.example.bookdemo.repository;

import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;
import com.example.bookdemo.entity.User;
import com.example.bookdemo.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    UserAuth findByUserId(Long id);

    // 在需要自定义查询方法时，可以在这里添加额外的方法声明
}