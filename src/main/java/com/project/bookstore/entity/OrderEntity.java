package com.project.bookstore.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    public OrderEntity() {
    }

    public OrderEntity(String orderId, String address, String userId, String modeOfPayment, double totalPrice) {
        this.orderId = orderId;
        this.address = address;
        this.userId = userId;
        this.modeOfPayment = modeOfPayment;
        this.totalPrice = totalPrice;
    }

    @Id
    private String orderId;

    private String address;

    @Column(updatable = false, insertable = false)
    private Timestamp orderDate;

    private String userId;

    private String modeOfPayment;

    private Double totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return orderDate != null ? format.format(orderDate) : "";
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }
}
