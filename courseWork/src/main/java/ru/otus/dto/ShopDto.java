package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import ru.otus.crm.model.Shop;

import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDto implements Comparable<ShopDto> {

    private Long price;
    private Integer amount;

    public ShopDto(Shop shop) {
        this.price = shop.getPrice();
        this.amount = shop.getAmount();
    }


    @Override
    public String toString() {
        return "Цена: " + price + " количество: " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDto shopDto = (ShopDto) o;
        return Objects.equals(price, shopDto.price) && Objects.equals(amount, shopDto.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, amount);
    }

    @Override
    public int compareTo(@NotNull ShopDto shopDto) {
        var result = this.price.compareTo(shopDto.getPrice());
        if (result == 0) {
            result = this.amount.compareTo(shopDto.getAmount());
        }
        return result;
    }
}
