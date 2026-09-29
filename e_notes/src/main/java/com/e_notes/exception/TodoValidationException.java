package com.e_notes.exception;

import lombok.Getter;

import java.util.Map;
@Getter
public class TodoValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public TodoValidationException(Map<String, String> errors) {
        super("Todo validation failed");
        this.errors = errors;
    }
}
