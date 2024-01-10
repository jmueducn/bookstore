package com.example.bookdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;


    @ManyToOne
    @JoinColumn(name="book_id",referencedColumnName = "id")
    private Book book;
    @Column(name ="quantity")
    private long quantity;

    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ...

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 构造方法

    public OrderItem(long id, Order order, long userId, Book bookId, long quantity, double totalPrice) {
        this.id = id;
        this.order = order;
        this.book = bookId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderItem() {

    }

    // Getter 和 Setter 方法

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }



    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
