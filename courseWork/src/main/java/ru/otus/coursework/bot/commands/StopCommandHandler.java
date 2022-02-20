package ru.otus.coursework.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.coursework.bot.CommandsHandler;
import ru.otus.coursework.enums.Commands;
import ru.otus.coursework.exceptions.AppUserNotFoundException;
import ru.otus.coursework.exceptions.CommandAlreadyDoneException;
import ru.otus.coursework.bot.toolbox.BotHelper;

public class StopCommandHandler extends ServiceCommandHandler {

    private static final String COMMAND_TEXT = "Вы отписались от рассылки цен товаров. Для возобновления рассылки напишите боту " + Commands.START.getCommand();
    private static final String ERROR_COMMAND_TEXT = "Вы уже отписаны от рассылки. Для возобновления напишите боту " + Commands.START.getCommand();
    private final CommandsHandler commandsHandler;

    public StopCommandHandler(CommandsHandler commandsHandler) {
        super(Commands.STOP);
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var userName = BotHelper.makeUserName(user);
        try {
            commandsHandler.handleStopCommand(user);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, COMMAND_TEXT);
        } catch (CommandAlreadyDoneException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_COMMAND_TEXT);
        } catch (AppUserNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_USER_NOT_FOUND_TEXT);
        } catch (Exception ex){
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }
    }
}
