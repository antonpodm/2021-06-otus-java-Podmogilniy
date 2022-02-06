package ru.otus.coursework.crm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.Set;

@Table("goods_info")
@Getter
@Builder(toBuilder = true)
@ToString
public class GoodInfo {
    @Id
    private Long id;
    @NonNull
    private Long outerId;
    @NonNull
    private String name;
    @NonNull
    private Long sellAveragePrice;
    @NonNull
    private Long buyAveragePrice;
    @MappedCollection(idColumn = "good_info_id")
    private Set<Shop> shops;

    @PersistenceConstructor
    public GoodInfo(Long id, @NonNull Long outerId, @NonNull String name, @NonNull Long sellAveragePrice, @NonNull Long buyAveragePrice, @NonNull Set<Shop> shops) {
        this.id = id;
        this.outerId = outerId;
        this.name = name;
        this.sellAveragePrice = sellAveragePrice;
        this.buyAveragePrice = buyAveragePrice;
        this.shops = shops;
    }

    public GoodInfo(@NonNull Long outerId, @NonNull String name, @NonNull Long sellAveragePrice, @NonNull Long buyAveragePrice, @NonNull Set<Shop> shops) {
        this(null, outerId, name, sellAveragePrice, buyAveragePrice, shops);
    }

}
