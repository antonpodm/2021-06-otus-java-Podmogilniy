package ru.otus.coursework.dto.enums;

import lombok.Getter;

@Getter
public enum DealType {
    SELL("Продажа"),
    BUY("Покупка");

    private final String type;

    DealType(String type) {
        this.type = type;
    }
}
