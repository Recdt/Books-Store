package com.example.booksstore.exceptions;

public class NonExistentRoleException extends RuntimeException {
    public NonExistentRoleException(String message) {
        super(message);
    }

    public NonExistentRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
