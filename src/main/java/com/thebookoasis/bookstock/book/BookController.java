package com.thebookoasis.bookstock.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
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
        model.addAttribute("books", books);
        return "all";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("book", book);
        bookRepository.add(book);
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
    public String editBook(@RequestParam Integer id, Model model, Book updatedBook) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        Book foundBook = book.get();
        model.addAttribute("book", foundBook);
        foundBook.setGenre(updatedBook.getGenre());
        foundBook.setPublishedYear(updatedBook.getPublishedYear());
        foundBook.setAuthor(updatedBook.getAuthor());
        foundBook.setTitle(updatedBook.getTitle());
        bookRepository.update(foundBook, id);
        return "redirect:/all";
    }

    // Using get mapping as request from button is get
    @GetMapping("/delete")
    public String deleteBook(@RequestParam Integer id) {
        bookRepository.delete(id);
        return "redirect:/all";
    }

    @GetMapping("/id")
    public String findById(@RequestParam Integer id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        // converted to list so data is displayed. Should I look at creating another
        // page.
        List<Book> bookList = Arrays.asList(book.get());
        model.addAttribute("books", bookList);
        return "book";
    }

    @GetMapping("/title")
    public String findByTitle(@RequestParam String title, Model model) {
        List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/author")
    public String findByAuthor(@RequestParam String author, Model model) {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/year")
    public String findByPublishedYear(@RequestParam Integer publishedYear, Model model) {
        List<Book> books = bookRepository.findByPublishedYear(publishedYear);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/genre")
    public String findByGenre(@RequestParam String genre, Model model) {
        List<Book> books = bookRepository.findByGenre(genre);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }
}
