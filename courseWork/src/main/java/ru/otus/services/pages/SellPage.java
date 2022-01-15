package ru.otus.services.pages;

import org.springframework.stereotype.Component;
import ru.otus.enums.DealType;

@Component
public class SellPage extends PageType {

    public SellPage() {
        super("table4", DealType.SELL);
    }

}
