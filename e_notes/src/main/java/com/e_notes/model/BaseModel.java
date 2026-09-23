package com.e_notes.model;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private LocalDateTime createdOn;
    private Integer updatedBY;
    private LocalDateTime updatedOn;

}
