package com.example.bookdemo.service;

import com.example.bookdemo.DTO.OrderRequest;
import com.example.bookdemo.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order placeOrder(OrderRequest orderRequest);
}
