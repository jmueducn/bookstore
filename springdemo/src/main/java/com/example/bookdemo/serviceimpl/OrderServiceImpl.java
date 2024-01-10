package com.example.bookdemo.serviceimpl;

import com.example.bookdemo.DAO.BookDAO;
import com.example.bookdemo.DAO.OrderDAO;
import com.example.bookdemo.DAO.OrderItemDAO;
import com.example.bookdemo.DTO.OrderRequest;
import com.example.bookdemo.entity.Book;
import com.example.bookdemo.entity.Order;
import com.example.bookdemo.entity.OrderItem;
import com.example.bookdemo.repository.OrderRepository;
import com.example.bookdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final BookDAO bookDAO;
    private final OrderItemDAO orderItemDAO;
    @Autowired
    public OrderServiceImpl( OrderDAO orderDAO, BookDAO bookDAO,OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO;
        this.bookDAO=bookDAO;
        this.orderItemDAO=orderItemDAO;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.findAll();

        for (Order order : orders) {
            List<OrderItem> orderItems = orderDAO.findByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }

        return orders;
    }
    @Override
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        Long userId = orderRequest.getUserId();

        // Create order entity
        Order order = new Order();
        order.setUserId(userId);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());

        // Create order item entities
        List<OrderItem> orderItems = new ArrayList<>();
        for (Book bookItem : orderRequest.getBooks()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(bookItem);
            orderItem.setQuantity(bookItem.getQuantities());
            orderItem.setTotalPrice(bookItem.getQuantities() * bookItem.getPrice());
            orderItem.setCreatedAt(order.getCreatedAt());
            orderItems.add(orderItem);
            Book booktosave=bookDAO.getBookById(bookItem.getId());
            booktosave.setQuantities(0);
            booktosave.setBuyed(bookItem.getBuyed());
            bookDAO.modifyBook(booktosave);
        }
        order.setOrderItems(orderItems);
        //  int result=10/0;
        orderDAO.placeOrder(order);

        //  int result=10/0;
        orderItemDAO.saveAll(order);

      return  order;
    }
}
