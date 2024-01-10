package com.example.bookdemo.repository;

import com.example.bookdemo.entity.BookIcon;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface BookIconRepository  extends MongoRepository<BookIcon, Integer> {
}
