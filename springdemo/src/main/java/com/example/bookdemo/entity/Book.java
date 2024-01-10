package com.example.bookdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import com.example.bookdemo.entity.BookIcon;
@Entity
@Table(name= "books" )
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private double price;
    @Column(name = "description")
    private String description;
    @Transient
    private String image;
    @Column(name = "quantities")
    private int quantities;
    @Column(name = "buyed")
    private int buyed;

    @Column(name = "tag")
    private String tag;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();


    public Book(int id, String title, String author, double price, String description, String image, int quantities, int buyed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantities = quantities;
        this.buyed = buyed;
    }

    public Book() {

    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    public int getBuyed() {
        return buyed;
    }

    public void setBuyed(int buyed) {
        this.buyed = buyed;
    }

    @Transient
    private BookIcon icon;
    @Transient
    public BookIcon getBookIcon(){
        return icon;
    }

    public void setBookIcon(BookIcon icon) {
        this.icon = icon;
    }
}
