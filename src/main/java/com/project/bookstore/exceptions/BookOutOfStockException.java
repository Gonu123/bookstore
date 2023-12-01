package com.project.bookstore.exceptions;

import java.util.List;

public class BookOutOfStockException extends Exception {
    public BookOutOfStockException(List<String> items) {
        super("Following items are out of stock " + items);
    }
}

