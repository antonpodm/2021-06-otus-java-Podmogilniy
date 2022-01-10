package ru.otus.enums;

import lombok.Getter;

@Getter
public enum DealType {
    SELL("продажа"),
    BUY("покупка");

    private final String type;

    DealType(String type) {
        this.type = type;
    }
}
