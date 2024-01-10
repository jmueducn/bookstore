package com.example.bookdemo.DAOimpl;

import com.example.bookdemo.DAO.OrderItemDAO;
import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;
import com.example.bookdemo.repository.OrderItemRepository;
import com.example.bookdemo.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    private final OrderItemRepository orderItemRepository;
    @Autowired
    public OrderItemDAOImpl(OrderItemRepository orderItemRepository) {

        this.orderItemRepository = orderItemRepository;
    }
    @Override
    @Transactional
    public List<OrderItem> saveAll(Order order) {

          //int result=10/0;
        return orderItemRepository.saveAll(order.getOrderItems());

    }



}
