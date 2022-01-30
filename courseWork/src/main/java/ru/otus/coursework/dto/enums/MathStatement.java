package ru.otus.coursework.dto.enums;

import lombok.Getter;

@Getter
public enum MathStatement {
    MORE("больше"),
    LESS("меньше");

    private final String description;

    MathStatement(String description) {
        this.description = description;
    }
}
