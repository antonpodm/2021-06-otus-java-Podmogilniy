package ru.otus.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.bot.CommandsHandler;
import ru.otus.enums.Commands;
import ru.otus.exceptions.AppUserNotFoundException;
import ru.otus.exceptions.GoodNotFoundException;
import ru.otus.toolbox.BotHelper;

public class ListCommand extends ServiceCommand {
    private final CommandsHandler commandsHandler;
    protected static final String ERROR_GOODS_NOT_FOUND_TEXT = "В вашем списке товаров отсутствуют товары. Для добавления товара напишите боту " + Commands.ADD_GOOD.getCommand();

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
