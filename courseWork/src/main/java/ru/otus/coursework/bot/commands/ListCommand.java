package ru.otus.coursework.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.coursework.bot.CommandsHandler;
import ru.otus.coursework.enums.Commands;
import ru.otus.coursework.exceptions.AppUserNotFoundException;
import ru.otus.coursework.exceptions.GoodNotFoundException;
import ru.otus.coursework.bot.toolbox.BotHelper;

public class ListCommand extends ServiceCommand {
    private final CommandsHandler commandsHandler;
    protected static final String ERROR_GOODS_NOT_FOUND_TEXT = "В вашем списке товаров отсутствуют записи. Для добавления товара напишите боту " + Commands.ADD_GOOD.getCommand();

    public ListCommand(CommandsHandler commandsHandler) {
        super(Commands.GOODS_LIST);
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var userName = BotHelper.makeUserName(user);
        try {
            var goods = commandsHandler.handleListCommand(user);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, goods);
        } catch (AppUserNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_USER_NOT_FOUND_TEXT);
        } catch (GoodNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_GOODS_NOT_FOUND_TEXT);
        } catch (Exception ex) {
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }
    }
}
