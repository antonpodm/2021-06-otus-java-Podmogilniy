package ru.otus.coursework.services.pages;

import org.springframework.stereotype.Component;
import ru.otus.coursework.dto.enums.DealType;

@Component
public class SellPage extends PageType {
    public SellPage() {
        super("table4", DealType.SELL);
    }
}
