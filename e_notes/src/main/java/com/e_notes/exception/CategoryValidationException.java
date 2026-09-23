package com.e_notes.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CategoryValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public CategoryValidationException(Map<String, String> errors) {
        super("Category validation failed");
        this.errors = errors;
    }

}
