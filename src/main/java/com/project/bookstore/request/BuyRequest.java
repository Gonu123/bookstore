package com.project.bookstore.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BuyRequest {

    @NotBlank(message = "The address is required.")
    private String address;

    @NotBlank(message = "The book list is required.")
    private List<ItemRequest> items;

    @NotBlank(message = "The mode of payment is required.")
    private String modeOfPayment;

    private Double price;

    public String getAddress() {
        return address;
    }

    public List<ItemRequest> getItems() {
        return items;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public Double getPrice() {
        return price;
    }

    public BuyRequest(String address, List<ItemRequest> items, String modeOfPayment, Double price) {
        this.address = address;
        this.items = items;
        this.modeOfPayment = modeOfPayment;
        this.price = price;
    }
}
