package ru.otus.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.otus.bot.CommandsHandler;
import ru.otus.enums.Commands;
import ru.otus.exceptions.AppUserNotFoundException;
import ru.otus.exceptions.CommandFormatException;
import ru.otus.exceptions.GoodsInfoNotFoundException;
import ru.otus.toolbox.BotHelper;

public class InfoCommand extends ServiceCommand{

    private final CommandsHandler commandsHandler;

    public InfoCommand(CommandsHandler commandsHandler) {
        super(Commands.GOOD_INFO);
        this.commandsHandler = commandsHandler;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var userName = BotHelper.makeUserName(user);
        try {
            var goodsInfo = commandsHandler.handleInfoCommand(user, strings);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, goodsInfo);
        } catch (CommandFormatException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_COMMAND_FORMAT_TEXT);
        } catch (AppUserNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_USER_NOT_FOUND_TEXT);
        } catch (GoodsInfoNotFoundException ex) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_GOODS_INFO_NOT_FOUND_TEXT);
        } catch (Exception ex){
            log.error(userName, ex);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, ERROR_EVERYTHING_COLLAPSED);
        }
    }
}
