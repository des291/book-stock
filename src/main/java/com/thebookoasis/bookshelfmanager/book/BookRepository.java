package com.thebookoasis.bookshelfmanager.book;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * This class provides functionality to interact with books in a database. It
 * allows users to add, edit, delete and find books as well as calulate counts
 * for different criteria.
 * 
 * @author Des Grainger
 */
@Repository
public class BookRepository {

        private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
        private final JdbcClient jdbcClient;

        public BookRepository(JdbcClient jdbcClient) {
                this.jdbcClient = jdbcClient;
        }

        /**
         * Method to selects all books from DB.
         * 
         * @return List of all books
         */
        public List<Book> findAll() {
                return jdbcClient.sql("SELECT * FROM Books")
                                .query(Book.class)
                                .list();
        }

        /**
         * Method to select book from DB by ID.
         * 
         * @param id ID of book to be found.
         * @return Result returned as an Optional type.
         */
        public Optional<Book> findById(int id) {
                return jdbcClient.sql("SELECT * FROM Books WHERE id = :id")
                                .param("id", id)
                                .query(Book.class)
                                .optional();
        }

        /**
         * Method to select book from DB by title.
         * 
         * @param title Title of book to be found.
         * @return Result returned as a list.
         */
        public List<Book> findByTitle(String title) {
                return jdbcClient.sql("SELECT * FROM Books WHERE UPPER(title) = UPPER(:title)")
                                .param("title", title)
                                .query(Book.class)
                                .list();
        }

        /**
         * Method to select book from DB by author.
         * 
         * @param author Author of books to be found.
         * @return Result returned as a list.
         */
        public List<Book> findByAuthor(String author) {
                return jdbcClient.sql("SELECT * FROM Books WHERE UPPER(author) = UPPER(:author)")
                                .param("author", author)
                                .query(Book.class)
                                .list();
        }

        /**
         * Method to select book from DB by genre.
         * 
         * @param genre Genre of books to be found.
         * @return Result returned as a list.
         */
        public List<Book> findByGenre(String genre) {
                return jdbcClient.sql("SELECT * FROM Books WHERE UPPER(genre) = UPPER(:genre)")
                                .param("genre", genre)
                                .query(Book.class)
                                .list();
        }

        /**
         * Method to select book from DB by year published.
         * 
         * @param publishedYear Year of publication.
         * @return Result returned as a list.
         */
        public List<Book> findByPublishedYear(int publishedYear) {
                return jdbcClient.sql("SELECT * FROM Books WHERE published_year = :publishedYear")
                                .param("publishedYear",
                                                publishedYear)
                                .query(Book.class)
                                .list();
        }

        /**
         * Method to add a book to the DB. Takes a Book object containing data inputted
         * by the user as a parameter. The ID of the book is generated automatically by
         * the DB.
         * 
         * @param book Book to be added to DB.
         */
        public void add(Book book) {
                var updated = jdbcClient.sql("INSERT INTO Books(title, author, published_year, genre) values(?,?,?,?)")
                                .params(List.of(book.getTitle(), book.getAuthor(), book.getPublishedYear(),
                                                book.getGenre().toString()))
                                .update();
                Assert.state(updated == 1, "Failed to create book " + book.getTitle());
        }

        /**
         * Method to update a book in the DB. Takes a Book object containing data
         * inputted
         * by the user as a parameter. Updates all fields of the record using the ID to
         * match.
         * 
         * @param book Book object containing updated data.
         * @param id   ID of book to be updated.
         */
        public void update(Book book, int id) {
                var updated = jdbcClient
                                .sql("UPDATE Books SET title = ?, author = ?, published_year = ?, genre = ? WHERE id = ?")
                                .params(List.of(book.getTitle(), book.getAuthor(), book.getPublishedYear(),
                                                book.getGenre().toString(),
                                                id))
                                .update();

                Assert.state(updated == 1, "Failed to update book " + book.getTitle());
        }

        /**
         * Method to delete a book from the DB.
         * 
         * @param id ID of book to be deleted.
         */
        public void delete(int id) {
                var updated = jdbcClient.sql("DELETE FROM Books WHERE id = :id")
                                .param("id", id)
                                .update();

                Assert.state(updated == 1, "Failed to delete book " + id);
        }

        public int countBooks() {
                return jdbcClient.sql("SELECT * FROM Books")
                                .query()
                                .listOfRows()
                                .size();
        }

        public int countAuthors() {
                return jdbcClient.sql("SELECT DISTINCT author FROM Books")
                                .query()
                                .listOfRows()
                                .size();
        }

        public int countGenres() {
                return jdbcClient.sql("SELECT DISTINCT genre FROM Books")
                                .query()
                                .listOfRows()
                                .size();
        }

}
