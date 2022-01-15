package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.Good;
import ru.otus.enums.DealType;
import ru.otus.enums.MathStatement;

import java.util.StringJoiner;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodDto {

    private Long id;
    private Long userId;
    private Long outerId;
    private DealType dealType;
    private MathStatement mathStatement;
    private Long price;
    private String name;

    public GoodDto(Good good) {
        this.id = good.getId();
        this.userId = good.getUserId();
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
