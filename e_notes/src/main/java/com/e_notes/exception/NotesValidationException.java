package com.e_notes.exception;

import lombok.Getter;

import java.util.Map;
@Getter
public class NotesValidationException extends RuntimeException{
    private final Map<String,String> errors;

    public NotesValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
}
