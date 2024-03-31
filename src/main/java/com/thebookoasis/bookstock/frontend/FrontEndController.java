package com.thebookoasis.bookstock.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.thebookoasis.bookstock.book.Book;
import com.thebookoasis.bookstock.book.BookNotFoundException;
import com.thebookoasis.bookstock.book.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FrontEndController {
    private static final Logger log = LoggerFactory.getLogger(FrontEndController.class);

    private final BookRepository bookRepository;

    public FrontEndController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        for (Book book : books) {
            log.info(book.toString());
        }
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
        return "redirect:/all";
    }

    @GetMapping("/find")
    public String findForm(Model model) {
        model.addAttribute("book", new Book());
        return "find";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Integer id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("book", book.get());
        return "edit";
    }

    @PostMapping("/edit")
    public String updateBook(@RequestParam Integer id, Model model, Book updatedBook) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        Book foundBook = book.get();
        model.addAttribute("book", foundBook);

        log.info(foundBook.toString());

        log.info(updatedBook.toString());
        foundBook.setGenre(updatedBook.getGenre());
        foundBook.setPublishedYear(updatedBook.getPublishedYear());
        foundBook.setAuthor(updatedBook.getAuthor());
        foundBook.setTitle(updatedBook.getTitle());
        log.info(foundBook.toString());

        bookRepository.update(foundBook, id);

        return "redirect:/all";
    }

    // Using get mapping as request from button is get
    @GetMapping("/delete")
    public String deleteBook(@RequestParam Integer id) {
        bookRepository.delete(id);
        return "redirect:/all";
    }

    // @GetMapping("/all")
    // public String allBooks(Model model) {
    // List<Book> books = bookRepository.findAll();
    // for (Book book : books) {
    // log.info(book.toString());
    // }
    // model.addAttribute("books", books);
    // return "all";
    // }

    @GetMapping("/id")
    public String findById(@RequestParam Integer id, Model model) {
        log.info("id:" + id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        // converted to list so data is displayed.
        List<Book> bookList = Arrays.asList(book.get());
        log.info(bookList.toString());
        model.addAttribute("books", bookList);
        return "book";
    }

    @GetMapping("/title")
    public String findByTitle(@RequestParam String title, Model model) {
        log.info("title:" + title);
        List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/author")
    public String findByAuthor(@RequestParam String author, Model model) {
        log.info("author:" + author);
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/year")
    public String findByPublishedYear(@RequestParam Integer publishedYear, Model model) {
        log.info("publishedYear:" + publishedYear);
        List<Book> books = bookRepository.findByPublishedYear(publishedYear);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/genre")
    public String findByGenre(@RequestParam String genre, Model model) {
        log.info("genre:" + genre);
        List<Book> books = bookRepository.findByGenre(genre);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }
}
