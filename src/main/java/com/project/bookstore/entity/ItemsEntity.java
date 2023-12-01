package com.project.bookstore.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ITEMS")
public class ItemsEntity {

    @EmbeddedId
    private ItemsId id;

    private Integer quantity;

    public ItemsEntity(String orderId, String isbn, Integer quantity) {
        this.id = new ItemsId(orderId, isbn);
        this.quantity = quantity;
    }

    public ItemsEntity() {
    }

    public ItemsId getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
