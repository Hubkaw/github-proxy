package com.fumology.githubproxy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GithubExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    public record ErrorResponse(int status, String message) {}
}
