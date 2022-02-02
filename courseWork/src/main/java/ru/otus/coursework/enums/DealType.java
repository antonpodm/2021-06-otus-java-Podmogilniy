package ru.otus.coursework.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum DealType {
    SELL("Продажа"),
    BUY("Покупка");

    private final String type;

    DealType(String type) {
        this.type = type;
    }

    public static Optional<DealType> findByType(String type){
        return Arrays.stream(DealType.values())
                .filter(dealType -> dealType.getType().equals(type))
                .findFirst();
    }
}
