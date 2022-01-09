package ru.otus.bot.parser;

import ru.otus.exceptions.CommandFormatException;

public abstract class ParsedCommand {

    protected void checkParamsAmount(String[] strings, int paramsAmount, String commandName) {
        if (strings.length < paramsAmount) {
            makeException(commandName);
        }
    }

    public void makeException(String commandName) {
        throw new CommandFormatException(commandName);
    }

}
