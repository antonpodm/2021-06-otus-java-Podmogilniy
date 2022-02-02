package ru.otus.coursework.dto;

import ru.otus.coursework.enums.DealType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class MessageDto {

    private final String name;
    private final DealType dealType;
    private final List<ShopDto> shops;

    public MessageDto(String name, DealType dealType, List<ShopDto> shops) {
        this.name = name;
        this.dealType = dealType;
        this.shops = new ArrayList<>();
        this.shops.addAll(shops);
        sortShops();
    }

    private void sortShops(){
        shops.sort(ShopDto::compareTo);
        if (DealType.BUY.equals(dealType)) {
            Collections.reverse(shops);
        }
    }
    @Override
    public String toString() {
        var joiner = new StringJoiner("\n");
        joiner.add("Название товара " + name);
        joiner.add(dealType.getType());
        for (ShopDto shop : shops) {
            joiner.add(shop.toString());
        }
        return joiner.toString();
    }

}
