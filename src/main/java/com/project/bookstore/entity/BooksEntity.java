package com.project.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOOK_DETAILS")
public class BooksEntity {

    public BooksEntity() {
    }

    public BooksEntity(String isbn, String name, String description, String author, String publicationYear, String imageUrlM, String imageUrlL, Double price, Integer booksAvailable, Double rating) {
        this.isbn = isbn;
        this.name = name;
        this.description = description;
        this.author = author;
        this.publicationYear = publicationYear;
        this.imageUrlM = imageUrlM;
        this.imageUrlL = imageUrlL;
        this.price = price;
        this.booksAvailable = booksAvailable;
        this.rating = rating;
    }

    @Id
    private String isbn;

    private String name;

    private String description;

    private String author;

    private String publicationYear;

    private String imageUrlM;

    private String imageUrlL;

    private Double price;

    private Integer booksAvailable;

    private Double rating;

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getImageUrlM() {
        return imageUrlM;
    }

    public String getImageUrlL() {
        return imageUrlL;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getBooksAvailable() {
        return booksAvailable;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

