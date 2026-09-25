package com.e_notes.exception;

import com.e_notes.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return CommonUtil.createErrorResponseMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }


    // Bean Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
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

        return CommonUtil.createErrorResponse(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }


    // Custom Category Validation Exception
    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<?> handleCategoryValidation(
            CategoryValidationException ex) {

        return CommonUtil.createErrorResponse(
                ex.getErrors(),
                HttpStatus.BAD_REQUEST
        );
    }


    // Existing Data Exception
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> existDataException(
            ExistDataException ex) {

        return CommonUtil.createErrorResponseMessage(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }


    // Invalid JSON / Request Body Exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadable(
            HttpMessageNotReadableException ex) {

        return CommonUtil.createErrorResponseMessage(
                "Invalid request body",
                HttpStatus.BAD_REQUEST
        );
    }


    // Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        return CommonUtil.createErrorResponseMessage(
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}