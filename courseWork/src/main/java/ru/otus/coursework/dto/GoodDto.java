package ru.otus.coursework.dto;

import ru.otus.coursework.crm.model.Good;
import ru.otus.coursework.dto.enums.DealType;
import ru.otus.coursework.dto.enums.MathStatement;

import java.util.StringJoiner;

public class GoodDto {

    private final Long outerId;
    private final DealType dealType;
    private final MathStatement mathStatement;
    private final Long price;
    private final String name;

    public GoodDto(Good good) {
        this.outerId = good.getOuterId();
        this.dealType = good.getDealType();
        this.mathStatement = good.getMathStatement();
        this.price = good.getPrice();
        this.name = good.getName();
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(" ");
        return joiner
                .add("Товар")
                .add(name)
                .add("с id")
                .add(outerId + ",")
                .add("тип сделки")
                .add(dealType.getType())
                .add(mathStatement.getDescription())
                .add(price + ".").toString();
    }
}
