package com.example.bookdemo.DAOimpl;

import com.example.bookdemo.DAO.OrderDAO;
import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;

import com.example.bookdemo.repository.OrderItemRepository;
import com.example.bookdemo.repository.OrderRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<Order> findAll() {
        return  orderRepository.findAll();

    }
    @Override
    public List<OrderItem> findByOrderId(Long id) {
        return orderItemRepository.findByOrderId(id);

    }
    @Transactional
    public Order placeOrder(Order order) {
         orderRepository.save(order);
     //    int result=10/0;
      return order;

    }


}
