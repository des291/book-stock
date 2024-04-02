package com.thebookoasis.bookstock.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book {
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotNull
    private Integer publishedYear;
    @NotEmpty
    private String genre;

    public Book() {
    };

    public Book(String title, String author, Integer publishedYear, String genre) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Book(Integer id, String title, String author, Integer publishedYear, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publishedYear=" + publishedYear
                + ", genre=" + genre
                + "]";
    }

}
