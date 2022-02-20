package ru.otus.coursework.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum DealType {
    SELL("Продажа"),
    BUY("Покупка");

    private final String description;

    DealType(String type) {
        this.description = type;
    }

    public static Optional<DealType> findByDescription(String description){
        return Arrays.stream(DealType.values())
                .filter(dealType -> dealType.getDescription().equals(description))
                .findFirst();
    }
}
