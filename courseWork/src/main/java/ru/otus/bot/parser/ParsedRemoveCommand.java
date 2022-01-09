package ru.otus.bot.parser;

import lombok.Getter;
import ru.otus.bot.CommandsList;

@Getter
public class ParsedRemoveCommand extends ParsedCommand {

    private final int PARAMS_AMOUNT = 1;
    private final String COMMAND_NAME = CommandsList.REMOVE;
    private Long outerId;

    public ParsedRemoveCommand(String[] strings) {
        checkParamsAmount(strings, PARAMS_AMOUNT, COMMAND_NAME);
        addOuterId(strings);
    }

    private void addOuterId(String[] strings) {
        try {
            outerId = Long.valueOf(strings[0]);
        } catch (Exception ex) {
            makeException(COMMAND_NAME);
        }
    }
}
