
package com.e_notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                "NOT_FOUND",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    // Bean Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    // Custom Category Validation Exception
    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<Map<String, String>> handleCategoryValidation(
            CategoryValidationException ex) {

        return new ResponseEntity<>(
                ex.getErrors(),
                HttpStatus.BAD_REQUEST
        );
    }

   /* @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<Map<String, String>> existDataException(
            ExistDataException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
   */ //}

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<Map<String, String>> existDataException(ExistDataException dataException) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", dataException.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> existDataException(HttpMessageNotReadableException dataException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", dataException.getMessage()));
    }


    // Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

        ErrorResponse response = new ErrorResponse(
                "Something went wrong",
                "INTERNAL_SERVER_ERROR",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }
}

