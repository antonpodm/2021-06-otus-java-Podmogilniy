package ru.otus.coursework.services.pages;

import org.springframework.stereotype.Component;
import ru.otus.coursework.dto.enums.DealType;

@Component
public class BuyPage extends PageType {
    public BuyPage() {
        super("table8", DealType.BUY);
    }
}
