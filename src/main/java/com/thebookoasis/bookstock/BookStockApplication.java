package com.thebookoasis.bookstock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thebookoasis.bookstock.book.Book;

@SpringBootApplication
public class BookStockApplication {

	private static final Logger log = LoggerFactory.getLogger(BookStockApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookStockApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Book book = new Book(1, "Klara and the Sun", "Kazuo Ishiguro", 2021, "Science Fiction");
			log.info("Book: " + book);
		};
	}
}
