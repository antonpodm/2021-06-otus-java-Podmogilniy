package ru.otus.coursework.crm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;
import ru.otus.coursework.enums.DealType;

@Table("shops")
@Getter
@Builder(toBuilder = true)
@ToString
public class Shop {

    @Id
    private final Long id;
    @NonNull
    private final Long goodInfoId;
    @NonNull
    private final Long price;
    @NonNull
    private final Integer amount;
    @NonNull
    private final DealType dealType;

    @PersistenceConstructor
    public Shop(Long id, @NonNull Long goodInfoId, @NonNull Long price, @NonNull Integer amount, @NonNull DealType dealType) {
        this.id = id;
        this.goodInfoId = goodInfoId;
        this.price = price;
        this.amount = amount;
        this.dealType = dealType;
    }

    public Shop(@NonNull Long goodInfoId, @NonNull Long price, @NonNull Integer amount, @NonNull DealType dealType) {
        this(null, goodInfoId, price, amount, dealType);
    }

}
