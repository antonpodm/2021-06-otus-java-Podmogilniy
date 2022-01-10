package ru.otus.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.bot.CommandsHandler;
import ru.otus.enums.Commands;
import ru.otus.exceptions.CommandAlreadyDoneException;
import ru.otus.toolbox.BotHelper;


public class StartCommand extends ServiceCommand {

    private static final String COMMAND_TEXT = "Давайте начнём! Вы подписались на обновление цен товаров. Для добавления товара для отслеживания напишите боту " + Commands.ADD_GOOD.getCommand();
    private static final String ERROR_COMMAND_TEXT = "Вы уже подписаны на рассылку. Для добавления товара напишите боту " + Commands.ADD_GOOD.getCommand();
    private final CommandsHandler commandsHandler;

    public StartCommand(CommandsHandler commandsHandler) {
        super(Commands.START);
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var userName = BotHelper.makeUserName(user);
        try {
            commandsHandler.handleStartCommand(user);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, COMMAND_TEXT);
        } catch (CommandAlreadyDoneException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_COMMAND_TEXT);
        } catch (Exception ex){
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }

    }

}
