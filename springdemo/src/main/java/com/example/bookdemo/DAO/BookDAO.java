package com.example.bookdemo.DAO;

import com.example.bookdemo.entity.Book;
import com.example.bookdemo.entity.Tag;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookDAO {
    List<Book> getAllBooks();
    Book getBookById(int id);
    Book addBook(Book book);

    void deleteBook(int id);
    void flashBookRedis();
    void modifyBook(Book book);

    List<Book>findBookByTag(String tag);
    List<Book>findBookByName(String name);
    List<Tag> findRelatedTags( String tagName);
}
