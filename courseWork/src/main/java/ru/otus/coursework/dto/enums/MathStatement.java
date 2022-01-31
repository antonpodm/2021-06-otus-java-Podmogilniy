package ru.otus.coursework.dto.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum MathStatement {
    MORE("больше"),
    LESS("меньше");

    private final String description;

    MathStatement(String description) {
        this.description = description;
    }

    public static Optional<MathStatement> findByDescription(String description){
        return Arrays.stream(MathStatement.values())
                .filter(mathStatement -> mathStatement.getDescription().equals(description))
                .findFirst();
    }
}
