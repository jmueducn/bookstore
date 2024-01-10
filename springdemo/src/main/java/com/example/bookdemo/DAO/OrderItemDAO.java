package com.example.bookdemo.DAO;

import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    List<OrderItem> saveAll(Order order);
}
