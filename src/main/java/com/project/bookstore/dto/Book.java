package com.project.bookstore.dto;

public record Book(String isbn, String author, String name, String imageUrlM, String imageUrlL, Double price, Integer booksAvailable, String publicationYear, String description, Double rating)
{

}
