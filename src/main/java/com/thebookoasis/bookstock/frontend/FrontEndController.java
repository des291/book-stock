package com.thebookoasis.bookstock.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.thebookoasis.bookstock.book.BookRepository;

@Controller
public class FrontEndController {

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
        return "layout";
    }
}
