package com.e_notes.util;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.NotesDto;
import com.e_notes.dto.TodoDto;
import com.e_notes.dto.UserDto;
import com.e_notes.exception.CategoryValidationException;
import com.e_notes.exception.NotesValidationException;
import com.e_notes.exception.TodoValidationException;
import com.e_notes.model.Role;
import com.e_notes.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    private final RoleRepository roleRepository;

    public Validation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // =========================
    // CATEGORY VALIDATION
    // =========================
    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (categoryDto == null) {

            errors.put("category", "Category data is required");

        } else {

            if (!StringUtils.hasText(categoryDto.getName())) {
                errors.put("name", "Category name is required");
            }

            if (!StringUtils.hasText(categoryDto.getDescription())) {
                errors.put(
                        "description",
                        "Category description is required"
                );
            }
        }

        if (!errors.isEmpty()) {
            throw new CategoryValidationException(errors);
        }
    }


    // =========================
    // NOTES VALIDATION
    // =========================
    public void notesValidation(NotesDto notesDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (notesDto == null) {

            errors.put("notes", "Notes data is required");

        } else {

            if (!StringUtils.hasText(notesDto.getTitle())) {
                errors.put("title", "Title is required");
            }

            if (!StringUtils.hasText(notesDto.getDescription())) {
                errors.put(
                        "description",
                        "Description is required"
                );
            }

            if (notesDto.getCategory() == null) {
                errors.put(
                        "category",
                        "Category is required"
                );
            }
        }

        if (!errors.isEmpty()) {
            throw new NotesValidationException(errors);
        }
    }


    // =========================
    // TODO VALIDATION
    // =========================
    public void todoValidation(TodoDto todoDto) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (todoDto == null) {

            errors.put("todo", "Todo data is required");

        } else {

            if (!StringUtils.hasText(todoDto.getTitle())) {
                errors.put(
                        "title",
                        "Todo title is required"
                );
            }

            if (todoDto.getStatus() == null) {

                errors.put(
                        "status",
                        "Status is required"
                );

            } else if (todoDto.getStatus().getId() == null) {

                errors.put(
                        "status.id",
                        "Status ID is required"
                );
            }
        }

        if (!errors.isEmpty()) {
            throw new TodoValidationException(errors);
        }
    }


    // =========================
    // USER VALIDATION
    // =========================
    public void userValidation(UserDto userDto) {

        // 1. Null check
        if (userDto == null) {
            throw new IllegalArgumentException(
                    "User data is required"
            );
        }


        // 2. First name
        if (!StringUtils.hasText(userDto.getFirstName())) {

            throw new IllegalArgumentException(
                    "First name is invalid"
            );
        }


        // 3. Last name
        if (!StringUtils.hasText(userDto.getLastName())) {

            throw new IllegalArgumentException(
                    "Last name is invalid"
            );
        }


        // 4. Email
        if (!StringUtils.hasText(userDto.getEmail())
                || !userDto.getEmail()
                .trim()
                .matches(Constant.EMAIL_REGEX)) {

            throw new IllegalArgumentException(
                    "Email is invalid"
            );
        }


        // 5. Mobile number
        if (!StringUtils.hasText(userDto.getMobNumber())
                || !userDto.getMobNumber()
                .trim()
                .matches(Constant.MOBNO_REGEX)) {

            throw new IllegalArgumentException(
                    "Mobile number is invalid"
            );
        }


        // 6. Password
        if (!StringUtils.hasText(userDto.getPassword())) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        if (userDto.getPassword().length() < 6) {

            throw new IllegalArgumentException(
                    "Password must be at least 6 characters"
            );
        }


        // 7. Role validation
        // Role validation
        if (CollectionUtils.isEmpty(userDto.getRoles())) {

            throw new IllegalArgumentException(
                    "At least one role is required"
            );
        }

        List<Integer> requestedRoleIds = userDto.getRoles()
                .stream()
                .map(UserDto.RoleDto::getId)
                .toList();

        if (requestedRoleIds.contains(null)) {

            throw new IllegalArgumentException(
                    "Role ID cannot be null"
            );
        }

        List<Role> existingRoles =
                roleRepository.findAllById(requestedRoleIds);

        if (existingRoles.size() != requestedRoleIds.size()) {

            throw new IllegalArgumentException(
                    "One or more role IDs are invalid: "
                            + requestedRoleIds
            );
        }
    }
}