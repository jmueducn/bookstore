package com.example.eurekaclientdem.repository;
import com.example.eurekaclientdem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);
    // 可以添加自定义查询方法
    // ...


}
