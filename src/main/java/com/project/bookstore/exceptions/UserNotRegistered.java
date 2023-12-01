package com.project.bookstore.exceptions;

public class UserNotRegistered extends Exception {
    public UserNotRegistered(String username) {
        super("User with username " + username + " not registered. Please Signup/Register to login");
    }
}

