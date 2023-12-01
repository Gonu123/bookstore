package com.project.bookstore.exceptions;

public class UserNameAlreadyTaken extends Exception {

    public UserNameAlreadyTaken(String username) {
        super("Username is already taken " + username);
    }
}
