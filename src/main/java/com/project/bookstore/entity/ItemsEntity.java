package com.project.bookstore.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ITEMS")
public class ItemsEntity {

    @EmbeddedId
    private ItemsId id;

    private Integer quantity;

    private Double price;

    public ItemsEntity(String orderId, String isbn, Integer quantity, double price) {
        this.id = new ItemsId(orderId, isbn);
        this.quantity = quantity;
        this.price = price;
    }

    public ItemsEntity() {
    }

    public ItemsId getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
