package com.thebookoasis.bookstock.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(BookRepository.class)
@AutoConfigureTestDatabase
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        bookRepository.create(new Book("The Shining", "Stephen King", 1980, "Horror"));
        bookRepository.create(new Book("Cash", "Johnny Cash", 2003, "Autobiography"));
    }

    @Test
    void testCountAuthors() {
        int count = bookRepository.countAuthors();
        assertEquals(3, count, "Should return 3");
    }

    @Test
    void testCountBooks() {
        int count = bookRepository.countBooks();
        assertEquals(3, count, "Should return 3");
    }

    @Test
    void testCountGenres() {
        int count = bookRepository.countGenres();
        assertEquals(3, count, "Should return 3");
    }

    @Test
    void testCreate() {
        bookRepository.create(new Book("Life 3.0", "Max Tegmark", 2018, "Non-fiction"));
        Book book = bookRepository.findById(4).get();
        assertEquals("Life 3.0", book.getTitle());
        assertEquals("Max Tegmark", book.getAuthor());
        assertEquals(2018, book.getPublishedYear());
        assertEquals("Non-fiction", book.getGenre());
        List<Book> books = bookRepository.findAll();
        assertEquals(4, books.size());
    }

    @Test
    void testDelete() {
        bookRepository.delete(3);
        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
        for (Book book : books) {
            assertTrue(book.getId() != 3);
        }
    }

    @Test
    void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertEquals(3, books.size());
    }

    @Test
    void testFindByAuthor() {
        List<Book> book = bookRepository.findByAuthor("Kazuo Ishiguro");
        assertEquals(1, book.size());
        assertEquals("Kazuo Ishiguro", book.get(0).getAuthor());
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        book = bookRepository.findByAuthor("Kazuo Ishiguro");
        assertEquals(2, book.size());
        book = bookRepository.findByAuthor("Zzz Zzz");
        assertTrue(book.isEmpty());
    }

    @Test
    void testFindByGenre() {
        List<Book> book = bookRepository.findByGenre("Science Fiction");
        assertEquals(1, book.size());
        assertEquals("Science Fiction", book.get(0).getGenre());
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        book = bookRepository.findByGenre("Science Fiction");
        assertEquals(2, book.size());
        book = bookRepository.findByGenre("Zzz");
        assertTrue(book.isEmpty());
    }

    @Test
    void testFindById() {
        Optional<Book> book = bookRepository.findById(3);
        assertEquals(3, book.get().getId());
        book = bookRepository.findById(99);
        assertTrue(book.isEmpty());
    }

    @Test
    void testFindByPublishedYear() {
        List<Book> book = bookRepository.findByPublishedYear(2021);
        assertEquals(1, book.size());
        assertEquals(2021, book.get(0).getPublishedYear());
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        book = bookRepository.findByGenre("Science Fiction");
        assertEquals(2, book.size());
        book = bookRepository.findByPublishedYear(999);
        assertTrue(book.isEmpty());
    }

    @Test
    void testFindByTitle() {
        List<Book> book = bookRepository.findByTitle("Klara and the Sun");
        assertEquals(1, book.size());
        assertEquals("Klara and the Sun", book.get(0).getTitle());
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        book = bookRepository.findByTitle("Klara and the Sun");
        assertEquals(2, book.size());
        book = bookRepository.findByTitle("Zzz");
        assertTrue(book.isEmpty());
    }

    @Test
    void testUpdate() {
        bookRepository.update(new Book("Life 3.0", "Max Tegmark", 2018, "Non-fiction"), 1);
        Book book = bookRepository.findById(1).get();
        assertEquals("Life 3.0", book.getTitle());
        assertEquals("Max Tegmark", book.getAuthor());
        assertEquals(2018, book.getPublishedYear());
        assertEquals("Non-fiction", book.getGenre());
    }
}
