package com.example.bookdemo.repository;

import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {




}
