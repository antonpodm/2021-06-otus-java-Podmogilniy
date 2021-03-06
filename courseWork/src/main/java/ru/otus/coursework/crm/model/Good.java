package ru.otus.coursework.crm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;
import ru.otus.coursework.bot.parser.ParsedAddCommand;
import ru.otus.coursework.enums.DealType;
import ru.otus.coursework.enums.MathStatement;

@Table("goods")
@Getter
@Builder(toBuilder = true)
@ToString
public class Good {

    @Id
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private Long outerId;
    @NonNull
    private DealType dealType;
    @NonNull
    private MathStatement mathStatement;
    @NonNull
    private Long price;
    private String name;

    public Good(@NonNull Long userId, @NonNull Long outerId, @NonNull DealType dealType, @NonNull MathStatement mathStatement, @NonNull Long price, String name) {
        this(null, userId, outerId, dealType, mathStatement, price, name);
    }

    @PersistenceConstructor
    public Good(Long id, @NonNull Long userId, @NonNull Long outerId, @NonNull DealType dealType, @NonNull MathStatement mathStatement, @NonNull Long price, String name) {
        this.id = id;
        this.userId = userId;
        this.outerId = outerId;
        this.dealType = dealType;
        this.mathStatement = mathStatement;
        this.price = price;
        this.name = name;
    }

}
