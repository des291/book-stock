package com.thebookoasis.bookstock.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book {
    @NotNull
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private Integer pubYear;
    @NotEmpty
    private String genre;

    public Book(Integer id, String title, String author, Integer pubYear, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubYear = pubYear;
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

    public int getPubYear() {
        return pubYear;
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

    public void setPubYear(Integer pubYear) {
        this.pubYear = pubYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
