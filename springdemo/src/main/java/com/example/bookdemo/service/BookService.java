package com.example.bookdemo.service;

import com.example.bookdemo.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(int id);

    Book addBook(Book book);

    void deleteBook(int id);

    void modifyBook(Book book);
    String getResult();
    List<Book>findBookByTag(String tag);
    List<Book>findBookByName(String name);
}
