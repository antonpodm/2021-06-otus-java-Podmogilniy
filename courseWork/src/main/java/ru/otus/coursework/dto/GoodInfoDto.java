package ru.otus.coursework.dto;

import ru.otus.coursework.crm.model.GoodInfo;
import ru.otus.coursework.crm.model.Shop;
import ru.otus.coursework.dto.enums.DealType;

import java.util.Comparator;
import java.util.List;

public class GoodInfoDto {

    private final Long outerId;
    private final String name;
    private final Long sellAveragePrice;
    private final Long buyAveragePrice;
    private final List<Shop> sortedSellShops;
    private final List<Shop> sortedBuyShops;

    public GoodInfoDto(GoodInfo goodInfo) {
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
