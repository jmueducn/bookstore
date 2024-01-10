package com.example.bookdemo.repository;
import com.example.bookdemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // 可以添加自定义查询方法
    // ...

    List<Book>findBooksByTag(String tag);

    List<Book> findBooksByTitle(String name);
}
