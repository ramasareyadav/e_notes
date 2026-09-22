package com.e_notes.model;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBY;
    private Integer updatedOn;

}
