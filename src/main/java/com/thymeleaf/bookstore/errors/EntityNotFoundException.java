package com.thymeleaf.bookstore.errors;

public class EntityNotFoundException extends Exception {

    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
