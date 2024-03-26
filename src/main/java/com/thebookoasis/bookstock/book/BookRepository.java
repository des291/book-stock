package com.thebookoasis.bookstock.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class BookRepository {

    private List<Book> books = new ArrayList<>();

    List<Book> findAll() {
        return books;
    }

    Book findById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .get();
    }

    @PostConstruct
    private void init() {
        books.add(new Book(1, "Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        books.add(new Book(2, "The Shining", "Stephen King", 1980, "Horror"));
        books.add(new Book(3, "Cash", "Johnny Cash", 2003, "Autobiography"));
    }
}
