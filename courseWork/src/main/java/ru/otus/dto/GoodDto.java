package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.Good;
import ru.otus.enums.DealType;
import ru.otus.enums.MathStatement;

@Data
@AllArgsConstructor
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

    public Good toGood() {
        return new Good(id, userId, outerId, dealType, mathStatement, price, name);
    }
}
