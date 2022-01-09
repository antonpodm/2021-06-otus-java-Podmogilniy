package ru.otus.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.bot.CommandsList;

abstract class ServiceCommand extends BotCommand {

    protected static final Logger log = LoggerFactory.getLogger(ServiceCommand.class);
    protected static final String ERROR_EVERYTHING_COLLAPSED = "Что-то сломалось. Если ошибка повторяется, обратитесь в поддержку бота.";
    protected static final String ERROR_USER_NOT_FOUND_TEXT = "Вы не зарегистрированы в системе. Для регистрации напишите боту " + CommandsList.START;
    protected static final String ERROR_GOOD_NOT_FOUND_TEXT = "Данный товар отсутствует в списке товаров. Для просмотра списка товаров напишите боту " + CommandsList.LIST;

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            absSender.execute(message);
        } catch (TelegramApiException ex) {
            log.error("commandName = {}, userName = {}",commandName, userName, ex);
        }
    }
}
