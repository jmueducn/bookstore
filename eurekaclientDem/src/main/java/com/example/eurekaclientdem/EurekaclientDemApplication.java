package com.example.eurekaclientdem;

import com.example.eurekaclientdem.entity.Book;
import com.example.eurekaclientdem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableDiscoveryClient
public class EurekaclientDemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaclientDemApplication.class, args);
    }
}

@RestController
@CrossOrigin(origins = "http://localhost:3000")
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private BookRepository bookRepository;
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
    @GetMapping("/byAuthor/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }
    @GetMapping("/byTitle/{title}")
    public List<String> getAuthorsByTitle(@PathVariable String title) {
        List<Book> books = bookRepository.findByTitle(title);
        List<String> authors = new ArrayList<>();
        for (Book book : books) {
            authors.add(book.getAuthor());
        }
        return authors;
    }
}
