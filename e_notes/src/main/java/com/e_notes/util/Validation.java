package com.e_notes.util;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.NotesDto;
import com.e_notes.exception.CategoryValidationException;
import com.e_notes.exception.NotesValidationException;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (categoryDto == null) {
            errors.put("category", "Category data is required");
        } else {

            if (categoryDto.getName() == null ||
                    categoryDto.getName().trim().isEmpty()) {

                errors.put("name", "Category name is required");
            }

            if (categoryDto.getDescription() == null ||
                    categoryDto.getDescription().trim().isEmpty()) {

                errors.put("description", "Category description is required");
            }
        }

        if (!errors.isEmpty()) {
            throw new CategoryValidationException(errors);
        }
    }


    public void notesValidation(NotesDto notesDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (notesDto == null) {

            errors.put("notes", "Notes data is required");

        } else {

            if (notesDto.getTitle() == null ||
                    notesDto.getTitle().trim().isEmpty()) {

                errors.put("title", "Title is required");
            }

            if (notesDto.getDescription() == null ||
                    notesDto.getDescription().trim().isEmpty()) {

                errors.put("description", "Description is required");
            }

            if (notesDto.getCategory() == null) {

                errors.put("category", "Category is required");
            }
        }

        if (!errors.isEmpty()) {
            throw new NotesValidationException(errors);
        }
    }
}