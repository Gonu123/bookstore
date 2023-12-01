package com.project.bookstore.util;

import com.project.bookstore.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtil {

    public static ResponseEntity<ErrorResponse> getErrorResponse(String errCode, String message, HttpStatus status) {
        ErrorResponse errResponse = new ErrorResponse(errCode, message);
        return ResponseEntity.status(status).body(errResponse);
    }

    public static ResponseEntity<ErrorResponse> getInternalServerErrorResponse(String message) {
        ErrorResponse errResponse = new ErrorResponse("INTERNAL_SERVER_SERVER", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

}
