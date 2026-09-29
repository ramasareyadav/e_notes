package com.e_notes.util;

import lombok.Getter;

@Getter
public enum TodoStatus {

    NOT_STARTED(1, "Not Started"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed");

    private final Integer id;
    private final String name;

    TodoStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}