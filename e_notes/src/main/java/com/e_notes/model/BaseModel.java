package com.e_notes.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseModel {

    @CreatedBy
    private Integer createdBy;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedBy
    private Integer updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedOn;
}

