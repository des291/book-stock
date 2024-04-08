package com.thebookoasis.bookshelfmanager.book;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * This class serves as the controller for managing the flow of data related to
 * books in the application.
 * 
 * It handles various HTTP requests to perform operations such as displaying
 * book details, adding, editing, and deleting books.
 * 
 * The controller interacts with the BookRepository to retrieve and update book
 * information in the database.
 * 
 * @author Des Grainger
 */
@Controller
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Method for GET requests on the homepage. Calculates counts for the number of
     * books, authors and genres and adds them to the model.
     * 
     * @param model
     * @return the index.html file
     */
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

    /**
     * Method for GET requests on '/all'. Adds a list of all books in the database
     * to the model.
     * 
     * @param model
     * @return the all.html file
     */
    @GetMapping("/all")
    public String allBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "all";
    }

    /**
     * Method for GET requestd on '/add'. Adds a blank Book object to the model.
     * 
     * @param model
     * @return the add.html file
     */
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add";
    }

    /**
     * Method for POST requests on '/add'. Adds the book to the database.
     * 
     * @param book  Book from the model. Contains user inputted values
     * @param model
     * @return redirects to '/all'
     */
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("book", book);
        bookRepository.add(book);
        return "redirect:/all";
    }

    /**
     * Method for GET requests on '/find'. Adds a blank Book to the model.
     * 
     * @param model
     * @return the find.html file
     */
    @GetMapping("/find")
    public String findForm(Model model) {
        model.addAttribute("book", new Book());
        return "find";
    }

    /**
     * Method for GET requests on '/edit'. Reads the ID value from the request
     * parameter, for example /edit?id=2. If the book is found it is added to the
     * model, otherwise a BookNotFoundException is thrown.
     * 
     * @param id    ID of the book to be edited. Read from request parameter.
     * @param model
     * @return the edit.html file
     */
    @GetMapping("/edit")
    public String editForm(@RequestParam Integer id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("book", book.get());
        return "edit";
    }

    /**
     * Method for POST requests on '/edit'. Reads the ID value from the request
     * parameter, for example /edit?id=2. If the book is found it is added to the
     * model, otherwise a BookNotFoundException is thrown. Updates the book in the
     * database with the matching ID based on user inputs.
     * 
     * @param id          ID of the book to be edited. Read from request parameter.
     * @param model
     * @param updatedBook Book object contining values from user inputs.
     * @return redirects to '/all'
     */
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

    /**
     * Method for GET requests on '/delete'. Reads the ID value from the request
     * parameter, for example /delete?id=2. If the book is found it is deleted from
     * the database, otherwise a BookNotFoundException is thrown.
     * 
     * Uses a @GetMapping since the request from the button is a GET request.
     * 
     * @param id ID of the book to be deleted. Read from request parameter.
     * @return redirects to '/all'
     */
    @GetMapping("/delete")
    public String deleteBook(@RequestParam Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        bookRepository.delete(id);
        return "redirect:/all";
    }

    /**
     * Method for GET requests on '/id'. Reads the ID value from the request
     * parameter, for example /id?id=2. If the book is found it is added to the
     * model, otherwise a BookNotFoundException is thrown.
     * 
     * @param id    ID of the book to be found. Read from request parameter.
     * @param model
     * @return the book.html file
     */
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

    /**
     * Method for GET requests on '/title'. Reads the Title value from the request
     * parameter, for example /title?title=Klara+and+the+Sun. If the book is found
     * it is added to the model, otherwise a BookNotFoundException is thrown.
     * 
     * @param title Title of the book to be found. Read from request parameter.
     * @param model
     * @return the book.html file
     */
    @GetMapping("/title")
    public String findByTitle(@RequestParam String title, Model model) {
        List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    /**
     * Method for GET requests on '/author'. Reads the Author value from the request
     * parameter, for example /author?author=Kazuo+Ishiguro. If the book
     * is found it is added to the model, otherwise a BookNotFoundException is
     * thrown.
     * 
     * @param author Author of the book to be found. Read from request parameter.
     * @param model
     * @return the book.html file
     */
    @GetMapping("/author")
    public String findByAuthor(@RequestParam String author, Model model) {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    /**
     * Method for GET requests on '/year'. Reads the Year value from the request
     * parameter, for example /year?year=2021. If the book
     * is found it is added to the model, otherwise a BookNotFoundException is
     * thrown.
     * 
     * @param publishedYear Year of publication of the book to be found. Read from
     *                      request parameter.
     * @param model
     * @return the book.html file
     */
    @GetMapping("/year")
    public String findByPublishedYear(@RequestParam Integer publishedYear, Model model) {
        List<Book> books = bookRepository.findByPublishedYear(publishedYear);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        model.addAttribute("books", books);
        return "book";
    }

    /**
     * Method for GET requests on '/genre'. Reads the Genre value from the request
     * parameter, for example /genre?genre=Science+Fiction. If the book
     * is found it is added to the model, otherwise a BookNotFoundException is
     * thrown.
     * 
     * @param genre Genre of the book to be found. Read from request parameter.
     * @param model
     * @return the book.html file
     */
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
