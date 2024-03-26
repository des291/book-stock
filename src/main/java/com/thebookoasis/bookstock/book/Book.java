package com.thebookoasis.bookstock.book;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer pubYear;
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
