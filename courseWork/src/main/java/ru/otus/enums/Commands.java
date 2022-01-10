package ru.otus.enums;

import lombok.Getter;

@Getter
public enum Commands {
    START("/start", "Начать трансляцию. ", "/start без аргументов"),
    STOP("/stop", "Остановить трансляцию. ", "/stop без аргументов"),
    HELP("/help", "Показать справку. ", "/help без аргументов"),
    ADD_GOOD("/add", "Добавить товар. ", "/add [название товара] [id товара] [тип сделки: "
            + DealType.SELL.name() + "/" + DealType.BUY.name() + "] [больше/меньше: "
            + MathStatement.MORE.name() + "/" + MathStatement.LESS.name() + "] [цена]"),
    REMOVE_GOOD("/remove", "Удалить товар. ", "/remove [id товара]"),
    GOODS_LIST("/list", "Список товаров. ", "/list без аргументов"),
    DELETE_USER("/delete_user", "Удалить пользователя. ", "/delete_user без аргументов"),
    GOOD_INFO("/info", "Показать информацию о товаре. ", "/info [id товара]");

    private final String command;
    private final String description;
    private final String format;

    Commands(String command, String description, String format) {
        this.command = command;
        this.description = description;
        this.format = format;
    }
}
