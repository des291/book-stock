package com.thebookoasis.bookstock.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.thebookoasis.bookstock.BookStockApplication;
import com.thebookoasis.bookstock.book.Book;
import com.thebookoasis.bookstock.book.BookController;
import com.thebookoasis.bookstock.book.BookNotFoundException;
import com.thebookoasis.bookstock.book.BookRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FrontEndController {
    private static final Logger log = LoggerFactory.getLogger(FrontEndController.class);

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
    public String bookForm(Model model) {
        model.addAttribute("book", new Book());
        return "create";
    }

    @PostMapping("/create")
    public String postBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("book", book);
        bookRepository.create(book);
        return "create-result";
    }

    @GetMapping("/find")
    public String findForm(Model model) {
        model.addAttribute("book", new Book());
        return "find";
    }

    @GetMapping("/id")
    public String findById(@RequestParam Integer id, Model model) {
        log.info("id:" + id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("book", book.get());
        return "book";
    }

    @GetMapping("/title")
    public String findByTitle(@RequestParam String title, Model model) {
        log.info("title:" + title);
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("book", book.get());
        return "book";
    }
}
