package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.enums.DealType;

import java.util.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {

    private String name;
    private DealType dealType;
    private List<ShopDto> shops;


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
