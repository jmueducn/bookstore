package com.example.bookdemo.DAOimpl;

import com.alibaba.fastjson2.JSON;
import com.example.bookdemo.DAO.BookDAO;
import com.example.bookdemo.entity.Book;
import com.example.bookdemo.entity.BookIcon;
import com.example.bookdemo.entity.Tag;
import com.example.bookdemo.repository.BookIconRepository;
import com.example.bookdemo.repository.BookRepository;
import com.example.bookdemo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Repository
public class BookDAOImpl implements BookDAO {

    private final BookRepository bookRepository;
    private final BookIconRepository bookIconRepository;

    private final TagRepository tagRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    public BookDAOImpl(BookRepository bookRepository,BookIconRepository bookIconRepository,TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.bookIconRepository=bookIconRepository;
        this.tagRepository=tagRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books;
        List<BookIcon> bis;

            System.out.println("Redis is not available, handle the exception gracefully");
            books = bookRepository.findAll();
            bis=bookIconRepository.findAll();
            // Create a Map for faster lookups of BookIcons by their IDs
            Map<Integer, BookIcon> iconMap = bis.stream()
                    .collect(Collectors.toMap(BookIcon::getId, Function.identity()));

            // Iterate over the books and set the corresponding BookIcon
            books.forEach(book -> {
                int iconId = book.getId(); // Assuming you have a method like this in your Book class
                BookIcon icon = iconMap.get(iconId);
                if (icon != null) {
                    book.setBookIcon(icon);
                }
            });

        return books;
    }
    @Override
    public void flashBookRedis(){
        List<Book>books = bookRepository.findAll();
        List<BookIcon> bis=bookIconRepository.findAll();
        // Create a Map for faster lookups of BookIcons by their IDs
        Map<Integer, BookIcon> iconMap = bis.stream()
                .collect(Collectors.toMap(BookIcon::getId, Function.identity()));

        // Iterate over the books and set the corresponding BookIcon
        books.forEach(book -> {
            int iconId = book.getId(); // Assuming you have a method like this in your Book class
            BookIcon icon = iconMap.get(iconId);
            if (icon != null) {
                book.setBookIcon(icon);
            }
        });
        String booksCacheKey = "books";
        try { redisTemplate.opsForValue().set(booksCacheKey, JSON.toJSONString(books));
        System.out.println("Books are added to Redis cache");}
        catch (Exception e) {
            System.out.println("Redis is not available, handle the exception gracefully");

        }

    }
    @Override
    public Book getBookById(int id) {
        Book book = null;
        BookIcon BI=null;
        try {
            String p = (String) redisTemplate.opsForValue().get("book" + id);
            if (p == null) {
                book = bookRepository.findById(id).orElse(null);
                BI = bookIconRepository.findById(id).orElse(null);
                book.setBookIcon(BI);

                if (book != null) {
                    redisTemplate.opsForValue().set("book" + id, JSON.toJSONString(book));
                    System.out.println(JSON.toJSONString(book));
                    System.out.println("ADD Book " + id + " in Redis");

                }
            } else {
                book = JSON.parseObject(p, Book.class);
                System.out.println("Book " + id + " is in Redis");
                System.out.println(JSON.toJSONString(book));
            }
        } catch (Exception e) {
            System.out.println("Redis is not available, handle the exception gracefully");
            book = bookRepository.findById(id).orElse(null);
            BI = bookIconRepository.findById(id).orElse(null);
            System.out.println(BI.getIcon_url());
            book.setBookIcon(BI);
        }

        return book;
    }
    @Override
    public Book addBook(Book book) {

        // Save the book to the database
        Book savedBook = bookRepository.save(book);
        BookIcon bi =new BookIcon(savedBook.getId(),book.getImage());
        book.setBookIcon(bi);
        BookIcon BI = bookIconRepository.save(book.getBookIcon());
        try {
            // Add the book to the Redis cache
            String bookJson = JSON.toJSONString(book);
            redisTemplate.opsForValue().set("book" + savedBook.getId(), bookJson);
            System.out.println("Book " + savedBook.getId() + " is added to Redis");

        } catch (Exception e) {
            System.out.println("Redis is not available, handle the exception gracefully");
            System.out.println("Failed to add book " + savedBook.getId() + " to Redis");
        }

        return savedBook;
    }
    @Override
    public void deleteBook(int id) {
        // Delete the book from the database
        bookRepository.deleteById(id);
        bookIconRepository.deleteById(id);
        try {
            // Delete the book from the Redis cache
            redisTemplate.delete("book" + id);
            System.out.println("Book " + id + " is deleted from Redis");

        } catch (Exception e) {
            System.out.println("Redis is not available, handle the exception gracefully");
            System.out.println("Failed to delete book " + id + " from Redis");
        }
    }
    @Override
    public void modifyBook(Book book) {
        // Save the book to the database
        // Update the book in the database
        Book savedBook =bookRepository.save(book);
        BookIcon bi =new BookIcon(savedBook.getId(),book.getImage());
        book.setBookIcon(bi);
        bookIconRepository.save(book.getBookIcon());
        try {
            // Update the book in the Redis cache
            String bookJson = JSON.toJSONString(book);
            redisTemplate.opsForValue().set("book" + savedBook.getId(), bookJson);
            System.out.println("Book " + book.getId() + " is updated in Redis");

        } catch (Exception e) {
            // Redis is not available, handle the exception gracefully
            System.out.println("Failed to update book " + book.getId() + " in Redis");
        }



    }
    @Override
    public List<Book>findBookByTag(String tag){
        List<Book> books= bookRepository.findBooksByTag(tag);
        for (Book relatedTag : books) {
            BookIcon bi=bookIconRepository.findById(relatedTag.getId()).orElse(null);
            relatedTag.setBookIcon(bi);
        }
        return books;
    };
    @Override
    public List<Book>findBookByName(String name){
        List<Book> books= bookRepository.findBooksByTitle(name);

        return books;
    };
    @Override
    public List<Tag> findRelatedTags(String tagName){
        return tagRepository.findRelatedTags(tagName);
    };

}

