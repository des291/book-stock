package com.thebookoasis.bookstock.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.thebookoasis.bookstock.book.Book;
import com.thebookoasis.bookstock.book.BookController;
import com.thebookoasis.bookstock.book.BookRepository;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FrontEndController {

    private final BookRepository bookRepository;
    private final BookController bookController;

    public FrontEndController(BookRepository bookRepository, BookController bookController) {
        this.bookRepository = bookRepository;
        this.bookController = bookController;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        int bookCount = bookRepository.countBooks();
        model.addAttribute("bookCount", bookCount);
        int authorCount = bookRepository.countAuthors();
        model.addAttribute("authorCount", authorCount);
        int genreCount = bookRepository.countGenres();
        model.addAttribute("genreCount", genreCount);
        return "index";
    }

    @GetMapping("/all")
    public String allBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "all";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        return "create";
    }

    @PostMapping("/create")
    public String postBook(@RequestBody String entity) {

        return entity;
    }

}
