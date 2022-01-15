package ru.otus.services.pages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.enums.DealType;

@RequiredArgsConstructor
@Getter
public abstract class PageType {

    protected final String tableName;
    protected final DealType dealType;
}
