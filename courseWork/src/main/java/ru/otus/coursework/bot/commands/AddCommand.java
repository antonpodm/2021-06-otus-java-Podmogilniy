package ru.otus.coursework.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.coursework.bot.CommandsHandler;
import ru.otus.coursework.dto.enums.Commands;
import ru.otus.coursework.exceptions.AppUserNotFoundException;
import ru.otus.coursework.exceptions.CommandFormatException;
import ru.otus.coursework.toolbox.BotHelper;

public class AddCommand extends ServiceCommand{

    private static final String COMMAND_TEXT = "Товар добавлен. Для отображения списка Ваших товаров напишите боту " + Commands.GOODS_LIST.getCommand();
    private final CommandsHandler commandsHandler;

    public AddCommand(CommandsHandler commandsHandler) {
        super(Commands.ADD_GOOD);
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var userName = BotHelper.makeUserName(user);
        try {
            commandsHandler.handleAddCommand(user, strings);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, COMMAND_TEXT);
        } catch (CommandFormatException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_COMMAND_FORMAT_TEXT);
        } catch (AppUserNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_USER_NOT_FOUND_TEXT);
        } catch (Exception ex){
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }
    }
}
