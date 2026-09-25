package com.e_notes.util;

import com.e_notes.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    // Success response with data
    public static ResponseEntity<?> createBuildResponse(
            Object data,
            HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .massage("success")
                .data(data)
                .build();

        return response.create();
    }

    // Success response with message
    public static ResponseEntity<?> createBuildResponseMessage(
            String message,
            HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .massage(message)
                .build();

        return response.create();
    }

    // Error response with data
    public static ResponseEntity<?> createErrorResponse(
            Object data,
            HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .massage("failed")
                .data(data)
                .build();

        return response.create();
    }

    // Error response with message
    public static ResponseEntity<?> createErrorResponseMessage(
            String message,
            HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .massage(message)
                .build();

        return response.create();
    }
}