package ru.otus.coursework.bot.parser;

import lombok.Getter;
import ru.otus.coursework.dto.enums.Commands;


public class ParsedInfoCommand extends ParsedCommand {


    private final int PARAMS_AMOUNT = 1;
    private final String COMMAND_NAME = Commands.GOOD_INFO.getCommand();
    @Getter
    private Long outerId;

    public ParsedInfoCommand(String[] strings) {
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
