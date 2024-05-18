package com.example.booksstore.exceptions;

public class NoSuchCategoryException extends RuntimeException {
    public NoSuchCategoryException(String message) {
        super(message);
    }

    public NoSuchCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
