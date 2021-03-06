package ru.otus.coursework.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.coursework.bot.commands.*;
import ru.otus.coursework.crm.model.AppUser;
import ru.otus.coursework.enums.Commands;
import ru.otus.coursework.exceptions.SendMessageException;

import java.util.List;


public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger log = LoggerFactory.getLogger(Bot.class);
    private static final String ASK_FOR_HELP_COMMAND_TEXT = "Для вызова справки напишите боту " + Commands.HELP.getCommand();

    private final String name;
    private final String token;

    public Bot(String name, String token, CommandsHandler commandsHandler) {
        this.name = name;
        this.token = token;
        registerCommands(commandsHandler);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId().toString();
            var sm = createMessage(chatId, ASK_FOR_HELP_COMMAND_TEXT);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error("execute", e);
            }
        }
    }

    public void sendToUser(@NonNull AppUser user, @NonNull String message) {
        var chatId = user.getChatId().toString();
        var sm = createMessage(chatId, message);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new SendMessageException(message, e);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void registerCommands(CommandsHandler commandsHandler) {
        register(new StartCommandHandler(commandsHandler));
        register(new StopCommandHandler(commandsHandler));
        register(new AddCommandHandler(commandsHandler));
        register(new RemoveCommandHandler(commandsHandler));
        register(new ListCommandHandler(commandsHandler));
        register(new DeleteUserCommandHandler(commandsHandler));
        register(new InfoCommandHandler(commandsHandler));
        register(new HelpCommand());
    }

    private SendMessage createMessage(String chatId, String message) {
        var sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(message);
        return sm;
    }

}
