package ru.otus.coursework.dto.enums;

import lombok.Getter;

@Getter
public enum Commands {
    START("/start", "Начать трансляцию. Создаёт Вам профиль в системе бота, если у Вас его ещё не было. Запускает отправку обновления цен товаров от бота.", ""),
    STOP("/stop", "Остановить трансляцию. Обновления цен товаров не будут приходить Вам от бота.", ""),
    HELP("/help", "Показать справку.", ""),
    ADD_GOOD("/add", "Добавить товар в список Ваших товаров. Формат команды указан ниже. Вы можете комбинировать параметры покупки/продажи с параметрами больше/меньше.",
            "\nПример команд: \n/add Ягода 607 " + DealType.SELL.getType() + " " + MathStatement.LESS.getDescription() + " 52000 " +
                    "\n/add Семечко 608 " + DealType.BUY.getType() + " " + MathStatement.MORE.getDescription() + " 20000"
    ),
    REMOVE_GOOD("/remove", "Удалить товар из списка Ваших товаров. ", "\nПример команды: /remove 607"),
    GOODS_LIST("/list", "Показать список Ваших товаров. ", ""),
    DELETE_USER("/delete_user", "Удалить всю информацию о Вашем пользователе. ", ""),
    GOOD_INFO("/info", "Показать всю информацию о товаре: среднюю цену покупки и продажи, а также все активные магазины с товаром. ", "\nПример команды: /info 607");

    private final String command;
    private final String description;
    private final String format;

    Commands(String command, String description, String format) {
        this.command = command;
        this.description = description;
        this.format = format;
    }
}
