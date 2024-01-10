package com.example.bookdemo.DTO;

import com.example.bookdemo.entity.Book;
import java.util.List;
public class OrderRequest {
    private Long userId;
    private List<Book> books;

    public OrderRequest(Long userId, List<Book> books) {
        this.userId = userId;
        this.books = books;
    }
    public OrderRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "userId=" + userId +
                ", books=" + books +
                '}';
    }
}
