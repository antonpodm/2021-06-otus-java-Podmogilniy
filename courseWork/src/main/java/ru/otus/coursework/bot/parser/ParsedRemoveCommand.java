package ru.otus.coursework.bot.parser;

import lombok.Getter;
import ru.otus.coursework.enums.Commands;

public class ParsedRemoveCommand extends ParsedCommand {

    private final int PARAMS_AMOUNT = 1;
    private final String COMMAND_NAME = Commands.REMOVE_GOOD.getCommand();
    @Getter
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
