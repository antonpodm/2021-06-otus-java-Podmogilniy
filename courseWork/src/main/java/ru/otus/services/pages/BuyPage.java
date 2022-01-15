package ru.otus.services.pages;

import org.springframework.stereotype.Component;
import ru.otus.enums.DealType;

@Component
public class BuyPage extends PageType {

    public BuyPage() {
        super("table8", DealType.BUY);
    }

}
