package com.thebookoasis.bookstock.frontend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebookoasis.bookstock.book.Book;
import com.thebookoasis.bookstock.book.BookRepository;

@WebMvcTest(FrontEndController.class)
public class FrontEndControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction"));
        bookRepository.create(new Book("The Shining", "Stephen King", 1980, "Horror"));
        bookRepository.create(new Book("Cash", "Johnny Cash", 2003, "Autobiography"));
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
                .andExpect(MockMvcResultMatchers.model().attribute("book", new Book()));
    }

    @Test
    void testDeleteBook() {

    }

    @Test
    void testEditForm() {

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
