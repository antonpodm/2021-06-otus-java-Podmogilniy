package ru.otus.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.bot.commands.AddCommand;
import ru.otus.bot.commands.RemoveCommand;
import ru.otus.bot.commands.StartCommand;
import ru.otus.bot.commands.StopCommand;

import java.util.List;


public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger log = LoggerFactory.getLogger(Bot.class);

    private final String name;
    private final String token;
    private final CommandsHandler commandsHandler;

    public Bot(String name, String token, CommandsHandler commandsHandler) {
        this.name = name;
        this.token = token;
        this.commandsHandler = commandsHandler;
        register(new StartCommand(commandsHandler));
        register(new StopCommand(commandsHandler));
        register(new AddCommand(commandsHandler));
        register(new RemoveCommand(commandsHandler));
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            SendMessage sm = new SendMessage();
            sm.enableMarkdown(true);
            sm.setChatId(chatId);
            sm.setText(message);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error("execute", e);
            }
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

}
