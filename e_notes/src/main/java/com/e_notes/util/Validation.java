package com.e_notes.util;

import com.e_notes.dto.CategoryDto;
import com.e_notes.exception.CategoryValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        // 1. Check CategoryDto
        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException(
                    "Category object shouldn't be null or empty"
            );
        }

        // 2. Validate name
        if (ObjectUtils.isEmpty(categoryDto.getName())
                || categoryDto.getName().trim().isEmpty()) {

            errors.put(
                    "name",
                    "Name field is empty or null"
            );
        } else if (categoryDto.getName().length() > 50) {

            errors.put(
                    "name",
                    "Name must not exceed 50 characters"
            );
        }

        // 3. Validate description
        if (ObjectUtils.isEmpty(categoryDto.getDescription())
                || categoryDto.getDescription().trim().isEmpty()) {

            errors.put(
                    "description",
                    "Description field is empty or null"
            );
        } else if (categoryDto.getDescription().length() > 50) {

            errors.put(
                    "description",
                    "Description must not exceed 50 characters"
            );
        }

        // 4. Throw exception if validation errors exist
        if (!errors.isEmpty()) {
            throw new CategoryValidationException(errors);
        }
    }
}

