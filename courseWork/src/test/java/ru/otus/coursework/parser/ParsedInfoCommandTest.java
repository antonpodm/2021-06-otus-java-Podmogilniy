package ru.otus.coursework.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.coursework.bot.parser.ParsedInfoCommand;
import ru.otus.coursework.exceptions.CommandFormatException;

public class ParsedInfoCommandTest {

    private static final String ID = "607";
    private static final String NOT_ID = "qwerty";
    private static final String[] rightFormatCommand = {ID};
    private static final String[] wrongFormatCommand = {NOT_ID};

    @Test
    void shouldParseCommandSingleNumberParam() {
        var parsedCommand = new ParsedInfoCommand(rightFormatCommand);
        Assertions.assertEquals(Long.valueOf(ID), parsedCommand.getOuterId());
    }

    @Test
    void shouldThrowCommandFormatExceptionOnStringParam() {
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedInfoCommand(wrongFormatCommand));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnEmptyParams() {
        var command = new String[1];
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedInfoCommand(command));
    }

    @Test
    void shouldParseCommandOnMoreThanOneParam() {
        var command = new String[2];
        command[0] = ID;
        command[1] = NOT_ID;
        var parsedCommand = new ParsedInfoCommand(command);
        Assertions.assertEquals(Long.valueOf(ID), parsedCommand.getOuterId());
    }
}
