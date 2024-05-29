package com.example.booksstore.exceptions;

public class NoSuchOrderItemException extends RuntimeException {
    public NoSuchOrderItemException(String message) {
        super(message);
    }

    public NoSuchOrderItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
