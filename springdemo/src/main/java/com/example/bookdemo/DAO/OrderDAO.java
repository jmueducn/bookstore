package com.example.bookdemo.DAO;

import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;

import java.util.List;

public interface OrderDAO {
    Order placeOrder(Order order);

    List<Order> findAll();

    List<OrderItem> findByOrderId(Long id);
}
