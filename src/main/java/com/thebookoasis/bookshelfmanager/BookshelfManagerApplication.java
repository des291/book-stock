package com.thebookoasis.bookshelfmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thebookoasis.bookshelfmanager.book.Book;
import com.thebookoasis.bookshelfmanager.book.BookRepository;

@SpringBootApplication
public class BookshelfManagerApplication {

	private static final Logger log = LoggerFactory.getLogger(BookshelfManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookshelfManagerApplication.class, args);
	}

	// Uncoment below to add some books on startup

	// @Bean
	// CommandLineRunner runner(BookRepository bookRepository) {
	// return args -> {
	// if (bookRepository.countBooks() == 0) {
	// bookRepository.create(new Book("Klara and the Sun", "Kazuo Ishiguro", 2021,
	// "Science Fiction"));
	// bookRepository.create(new Book("The Shining", "Stephen King", 1980,
	// "Horror"));
	// bookRepository.create(new Book("Cash", "Johnny Cash", 2003,
	// "Autobiography"));
	// } else {
	// log.info("Not adding books as data already present");
	// }
	// };
	// }
}
