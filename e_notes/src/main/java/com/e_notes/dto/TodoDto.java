package com.e_notes.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private Integer id;

    private String title;

    private StatusDto status;

    private Integer createdBy;

    private LocalDateTime createdOn;

    private Integer updatedBy;

    private LocalDateTime updatedOn;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusDto{
        private Integer id;
        private String name;
    }

}
