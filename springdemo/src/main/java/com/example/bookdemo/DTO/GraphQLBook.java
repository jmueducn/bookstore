package com.example.bookdemo.DTO;

import com.example.bookdemo.entity.Book;
import lombok.Data;

@Data
public class GraphQLBook {
    private int id;
    private String name;
    private String author;
    // 其他字段

    public GraphQLBook(Book bookEntity) {
        this.id = bookEntity.getId();
        this.name = bookEntity.getTitle(); // 假设Book实体中的书名字段是title
        this.author = bookEntity.getAuthor();
        // 设置其他字段
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
// Getters and Setters
}
