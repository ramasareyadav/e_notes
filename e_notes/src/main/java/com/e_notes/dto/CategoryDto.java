package com.e_notes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

//    @NotBlank
//    @Min(value = 0)
//    @Max(value = 50)
    private String name;

//    @NotBlank
//    @Min(value = 10)
//    @Max(value = 11)
    private String description;
    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBY;
    private Integer updatedOn;

}
