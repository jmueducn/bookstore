package com.example.bookdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.bookdemo.repository")
public class BookdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookdemoApplication.class, args);
    }

}
