package com.project.bookstore.controller;

import com.project.bookstore.dto.Order;
import com.project.bookstore.dto.Orders;
import com.project.bookstore.request.BuyRequest;
import com.project.bookstore.response.BuyResponse;
import com.project.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrdersController {

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/order")
    public ResponseEntity<?> createOrder(@RequestAttribute("username") String username, @RequestBody BuyRequest buyRequest) {
        if (username == null || username.equals("")) {
            String body =  "{\"errCode\": \"INTERNAL_SERVER_ERROR\", \"message\":\"username expected in context\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }

        try {
            BuyResponse buyResponse = orderService.order(username, buyRequest);
            return ResponseEntity.ok(buyResponse);
        } catch (Exception ex) {
            String body =  "{\"errCode\": \"INTERNAL_SERVER_ERROR\", \"message\":\"" + ex.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<?> getOrders(@RequestAttribute("username") String username) {
        if (username == null || username.equals("")) {
            String body =  "{\"errCode\": \"INTERNAL_SERVER_ERROR\", \"message\":\"username expected in context\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }

        try {
            List<Order> orders = orderService.getOrders(username);
            return ResponseEntity.ok(new Orders(orders));
        } catch (Exception ex) {
            String body =  "{\"errCode\": \"INTERNAL_SERVER_ERROR\", \"message\":\"" + ex.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

}
