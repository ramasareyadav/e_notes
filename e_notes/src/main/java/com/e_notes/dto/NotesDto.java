package com.e_notes.dto;

import com.e_notes.model.Category;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    private Integer id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    private CategoryDto category;

    private FilesDto fileDetails;

    @NotNull(message = "Category is required")
    private Integer createdBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;

    private Boolean isDeleted;

    private LocalDateTime deletedOn;

    private Boolean isFavourite;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilesDto{
        private Integer id;
        private String originalFileName;
        private String displayName;
    }

}
