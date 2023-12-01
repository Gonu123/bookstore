package com.project.bookstore.controller;

import com.project.bookstore.dto.User;
import com.project.bookstore.exceptions.UserNameAlreadyTaken;
import com.project.bookstore.exceptions.UserNotRegistered;
import com.project.bookstore.request.UserRequest;
import com.project.bookstore.util.ErrorUtil;
import com.project.bookstore.response.UserResponse;
import com.project.bookstore.service.IdpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class IdpController {

    private IdpService idpService;

    public IdpController(final IdpService idpService) {
        this.idpService = idpService;
    }

    @PostMapping("idp/user")
    @CrossOrigin
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest user) {
        try {
            UserResponse response = idpService.createUser(new User("", user.username, user.name, user.phNo, "pwd"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNameAlreadyTaken e) {
            return ErrorUtil.getErrorResponse("USERNAME_ALREADY_TAKEN", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("idp/user")
    @CrossOrigin
    public ResponseEntity<?> getUser(@RequestAttribute("username") String username) {
        if (username == null || username.equals("")) {
            return ErrorUtil.getInternalServerErrorResponse("username expected in context");
        }

        try {
            UserResponse response = idpService.getUserByUsername(username);
            return ResponseEntity.ok(response);
        } catch (UserNotRegistered e) {
            return ErrorUtil.getErrorResponse("USER_NOT_REGISTERED", e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
