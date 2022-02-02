package ru.otus.coursework.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.coursework.bot.CommandsHandler;
import ru.otus.coursework.enums.Commands;
import ru.otus.coursework.exceptions.CommandAlreadyDoneException;
import ru.otus.coursework.toolbox.BotHelper;


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
            commandsHandler.handleStartCommand(user, chat);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, COMMAND_TEXT);
        } catch (CommandAlreadyDoneException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_COMMAND_TEXT);
        } catch (Exception ex){
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }

    }

}
