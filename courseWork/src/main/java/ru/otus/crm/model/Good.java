package ru.otus.crm.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;
import ru.otus.enums.DealType;
import ru.otus.enums.MathStatement;

@Table("goods")
@Getter
@Builder
public class Good {

    @Id
    private Long id;
    @NonNull
    private Long profileId;
    @NonNull
    private Long outerId;
    @NonNull
    private DealType dealType;
    @NonNull
    private MathStatement mathStatement;
    @NonNull
    private Long price;
    private String name;

    public Good(@NonNull Long profileId, @NonNull Long outerId, @NonNull DealType dealType, @NonNull MathStatement mathStatement, @NonNull Long price, String name) {
        this(null, profileId, outerId, dealType, mathStatement, price, name);
    }

    @PersistenceConstructor
    public Good(Long id, @NonNull Long profileId, @NonNull Long outerId, @NonNull DealType dealType, @NonNull MathStatement mathStatement, @NonNull Long price, String name) {
        this.id = id;
        this.profileId = profileId;
        this.outerId = outerId;
        this.dealType = dealType;
        this.mathStatement = mathStatement;
        this.price = price;
        this.name = name;
    }

}
