package com.example.bookdemo.controller;

import com.example.bookdemo.DTO.GraphQLBook;
import com.example.bookdemo.entity.Book;
import com.example.bookdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/mapreduce")
    public String getResult() {
        return bookService.getResult();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        book.setQuantities(0);
        return bookService.addBook(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }
    @PutMapping("/books/{id}")
    public void modifyBook(@RequestBody Book book, @PathVariable int id) {
        book.setId(id);
        book.setQuantities(0);
        bookService.modifyBook(book);
    }

    @GetMapping("/books/tag/{tag}")
    public List<Book> findBookByTag(@PathVariable String tag){
        return bookService.findBookByTag(tag);

    }
    @QueryMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<GraphQLBook> booksByName(@Argument String name) {
        System.out.println("Searching for books with name: " + name);
        return bookService.findBookByName(name)
                .stream()
                .map(GraphQLBook::new)
                .collect(Collectors.toList());
    }
}
