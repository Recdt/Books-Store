package com.example.booksstore.exceptions;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String message) {
        super(message);
    }

    public CategoryNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
