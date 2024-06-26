package com.thebookoasis.bookshelfmanager.book;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebookoasis.bookshelfmanager.book.Book;
import com.thebookoasis.bookshelfmanager.book.BookController;
import com.thebookoasis.bookshelfmanager.book.BookRepository;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.add(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        bookRepository.add(new Book("The Shining", "Stephen King", 1980, "Horror"));
        bookRepository.add(new Book("Cash", "Johnny Cash", 2003, "Autobiography"));
    }

    @Test
    void testAllBooks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"));
    }

    @Test
    void testBookForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.model().attribute("book", Matchers.instanceOf(Book.class)));
    }

    @Test
    void testDeleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/delete?id=1"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/all"));
    }

    @Test
    void testEditForm() throws Exception {
        log.info(bookRepository.findAll().toString());
        mvc.perform(MockMvcRequestBuilders.get("/edit?id=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.model().attribute("book", Matchers.instanceOf(Book.class)));
    }

    @Test
    void testFindByAuthor() {

    }

    @Test
    void testFindByGenre() {

    }

    @Test
    void testFindById() {

    }

    @Test
    void testFindByPublishedYear() {

    }

    @Test
    void testFindByTitle() {

    }

    @Test
    void testFindForm() {

    }

    @Test
    void testHomepage() {

    }

    @Test
    void testPostBook() {

    }

    @Test
    void testUpdateBook() {

    }
}
