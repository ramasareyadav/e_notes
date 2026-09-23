package com.e_notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private String description;

    private Boolean isActive;

    private Boolean isDeleted;

    private Integer createdBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;
}

