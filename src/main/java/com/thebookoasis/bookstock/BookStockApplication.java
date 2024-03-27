package com.thebookoasis.bookstock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thebookoasis.bookstock.book.Book;
import com.thebookoasis.bookstock.book.BookRepository;

@SpringBootApplication
public class BookStockApplication {

	private static final Logger log = LoggerFactory.getLogger(BookStockApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookStockApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BookRepository bookRepository) {
		return args -> {
			if (bookRepository.count() == 0) {
				Book book = new Book(1, "Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction");
				log.info("Book: " + book);
				bookRepository.create(book);
				Book book2 = new Book(2, "The Shining", "Stephen King", 1980, "Horror");
				bookRepository.create(book2);
				Book book3 = new Book(3, "Cash", "Johnny Cash", 2003, "Autobiography");
				bookRepository.create(book3);
			} else {
				log.info("Not adding books as data already present");
			}
		};
	}
}
