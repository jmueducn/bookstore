package com.example.bookdemo.controller;

import com.example.bookdemo.DTO.OrderRequest;
import com.example.bookdemo.entity.Book;
import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;
import com.example.bookdemo.service.OrderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from port 3000
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper; // 添加ObjectMapper
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public OrderController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/putorders")
    public Map<String, String> placeOrder(@RequestBody OrderRequest request, HttpServletResponse response) throws JsonProcessingException {
        // Retrieve userId from request
        String orderJson = objectMapper.writeValueAsString(request);
        kafkaTemplate.send("topic1",  "key", orderJson);
       // Order neworder=orderService.placeOrder(request);

        // Set allowed origin for requests from port 3000
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        // Return response
        Map<String, String> responseMap = new HashMap<>();
        String theStr="ORDER Confirmed,please view it in detail at'Orders' column later";
       // theStr+=neworder.getId();
        responseMap.put("message", theStr);
        return responseMap;
    }

}
