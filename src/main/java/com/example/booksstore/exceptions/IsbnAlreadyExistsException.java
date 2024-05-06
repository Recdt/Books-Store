package com.example.booksstore.exceptions;

public class IsbnAlreadyExistsException extends RuntimeException {
    public IsbnAlreadyExistsException(String message) {
        super(message);
    }

    public IsbnAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
