package com.e_notes.util;

import com.e_notes.dto.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public static MediaType getContentType(String originalFileName) {

        String extension = FilenameUtils
                .getExtension(originalFileName)
                .toLowerCase();

        switch (extension) {

            case "pdf":
                return MediaType.APPLICATION_PDF;

            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;

            case "png":
                return MediaType.IMAGE_PNG;

            case "gif":
                return MediaType.IMAGE_GIF;

            case "txt":
                return MediaType.TEXT_PLAIN;

            case "html":
            case "htm":
                return MediaType.TEXT_HTML;

            case "json":
                return MediaType.APPLICATION_JSON;

            case "xml":
                return MediaType.APPLICATION_XML;

            case "csv":
                return MediaType.parseMediaType("text/csv");

            case "zip":
                return MediaType.parseMediaType("application/zip");

            case "doc":
                return MediaType.parseMediaType(
                        "application/msword"
                );

            case "docx":
                return MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                );

            case "xls":
                return MediaType.parseMediaType(
                        "application/vnd.ms-excel"
                );

            case "xlsx":
                return MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                );

            case "ppt":
                return MediaType.parseMediaType(
                        "application/vnd.ms-powerpoint"
                );

            case "pptx":
                return MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                );

            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString(); // http:localhost:8080/api/v1/auth
        apiUrl=apiUrl.replace(request.getServletPath(),""); // http:localhost:8080
        return apiUrl;
    }
}