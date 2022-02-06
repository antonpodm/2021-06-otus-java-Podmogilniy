package ru.otus.coursework.bot.parser;

import ru.otus.coursework.exceptions.CommandFormatException;

public abstract class ParsedCommand {

    protected void checkParamsAmount(String[] strings, int paramsAmount, String commandName) {
        if (strings.length < paramsAmount) {
            makeCommandFormatException(commandName);
        }
    }

    public void makeCommandFormatException(String commandName) {
        throw new CommandFormatException(commandName);
    }

}
