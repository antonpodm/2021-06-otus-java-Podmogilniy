package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.GoodInfo;
import ru.otus.crm.model.Shop;
import ru.otus.enums.DealType;
import ru.otus.enums.MathStatement;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodInfoDto {

    private Long id;
    private Long outerId;
    private String name;
    private Long sellAveragePrice;
    private Long buyAveragePrice;
    private MathStatement mathStatement;
    private List<Shop> sortedSellShops;
    private List<Shop> sortedBuyShops;

    public GoodInfoDto(GoodInfo goodInfo) {
        this.id = goodInfo.getId();
        this.outerId = goodInfo.getOuterId();
        this.name = goodInfo.getName();
        this.sellAveragePrice = goodInfo.getSellAveragePrice();
        this.buyAveragePrice = goodInfo.getBuyAveragePrice();
        this.sortedSellShops = goodInfo.getShops().stream()
                .filter(shop -> shop.getDealType().equals(DealType.SELL))
                .sorted(Comparator.comparingLong(Shop::getPrice))
                .toList();
        this.sortedBuyShops = goodInfo.getShops().stream()
                .filter(shop -> shop.getDealType().equals(DealType.BUY))
                .sorted(Comparator.comparingLong(Shop::getPrice).reversed())
                .toList();
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder
                .append("Товар ").append(name).append(" с id ").append(outerId).append(".\n")
                .append("Продажа: \n")
                .append("Средняя цена ").append(sellAveragePrice).append(".\n")
                .append("Последние магазины: \n");
        for (Shop shop : sortedSellShops) {
            builder.append(new ShopDto(shop)).append("\n");
        }

        builder.append("Покупка: \n")
                .append("Средняя цена ").append(buyAveragePrice).append(".\n")
                .append("Последние магазины: \n");
        for (Shop shop : sortedBuyShops) {
            builder.append(new ShopDto(shop)).append("\n");
        }
        return builder.toString();
    }
}
