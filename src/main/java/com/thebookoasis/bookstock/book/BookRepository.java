package com.thebookoasis.bookstock.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.thebookoasis.bookstock.BookStockApplication;

import jakarta.annotation.PostConstruct;

@Repository
public class BookRepository {

    // private List<Book> books = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
    private final JdbcClient jdbcClient;

    public BookRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Book> findAll() {
        return jdbcClient.sql("SELECT * FROM Books")
                .query(Book.class)
                .list();
    }

    public Optional<Book> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM Books WHERE id = :id")
                .param("id", id)
                .query(Book.class)
                .optional();
    }

    public Optional<Book> findByTitle(String title) {
        return jdbcClient.sql("SELECT * FROM Books WHERE title = :title")
                .param("title", title)
                .query(Book.class)
                .optional();
    }

    // public void create(Book book) {
    // var updated = jdbcClient.sql("INSERT INTO Books(id, title, author, pub_year,
    // genre) values(?,?,?,?,?)")
    // .params(List.of(book.getId(), book.getTitle(), book.getAuthor(),
    // book.getPubYear(),
    // book.getGenre().toString()))
    // .update();

    // Assert.state(updated == 1, "Failed to create book " + book.getTitle());
    // }

    public void create(Book book) {
        var updated = jdbcClient.sql("INSERT INTO Books(title, author, pub_year, genre) values(?,?,?,?)")
                .params(List.of(book.getTitle(), book.getAuthor(), book.getPubYear(),
                        book.getGenre().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create book " + book.getTitle());
    }

    public void update(Book book, Integer id) {
        var updated = jdbcClient.sql("UPDATE Books SET title = ?, author = ?, pub_year = ?, genre = ? WHERE id = ?")
                .params(List.of(book.getTitle(), book.getAuthor(), book.getPubYear(), book.getGenre().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update book " + book.getTitle());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM Books WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete book " + id);
    }

    public int countBooks() {
        return jdbcClient.sql("SELECT * FROM Books")
                .query()
                .listOfRows()
                .size();
    }

    public int countAuthors() {
        return jdbcClient.sql("SELECT DISTINCT author FROM Books")
                .query()
                .listOfRows()
                .size();
    }

    public int countGenres() {
        return jdbcClient.sql("SELECT DISTINCT genre FROM Books")
                .query()
                .listOfRows()
                .size();
    }
    // @PostConstruct
    // private void init() {
    // books.add(new Book(1, "Klara and the Sun", "Kazuo Ishiguro", 2021, "Science
    // Fiction"));
    // books.add(new Book(2, "The Shining", "Stephen King", 1980, "Horror"));
    // books.add(new Book(3, "Cash", "Johnny Cash", 2003, "Autobiography"));
    // }
}
