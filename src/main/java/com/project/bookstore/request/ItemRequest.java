package com.project.bookstore.request;

public record ItemRequest(String isbn, int quantity, Double price) {
}
