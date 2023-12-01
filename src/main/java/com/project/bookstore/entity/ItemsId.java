package com.project.bookstore.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ItemsId implements Serializable {
        public ItemsId(String orderId, String isbn) {
                this.orderId = orderId;
                this.bookId = isbn;
        }

        public ItemsId() {
        }

        private String orderId;

        private String bookId;

        public String getOrderId() {
                return orderId;
        }

        public String getBookId() {
                return bookId;
        }
}
