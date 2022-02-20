package ru.otus.coursework.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.coursework.bot.parser.ParsedRemoveCommand;
import ru.otus.coursework.exceptions.CommandFormatException;

public class ParsedRemoveCommandTest {

    private static final String ID = "6070";
    private static final String NOT_ID = "qwert2y";
    private static final String[] rightFormatCommand = {ID};
    private static final String[] wrongFormatCommand = {NOT_ID};

    @Test
    void shouldParseCommandSingleNumberParam() {
        var parsedCommand = new ParsedRemoveCommand(rightFormatCommand);
        Assertions.assertEquals(Long.valueOf(ID), parsedCommand.getOuterId());
    }

    @Test
    void shouldThrowCommandFormatExceptionOnStringParam() {
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedRemoveCommand(wrongFormatCommand));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnEmptyParams() {
        var command = new String[1];
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedRemoveCommand(command));
    }

    @Test
    void shouldParseCommandOnMoreThanOneParam() {
        var command = new String[2];
        command[0] = ID;
        command[1] = NOT_ID;
        var parsedCommand = new ParsedRemoveCommand(command);
        Assertions.assertEquals(Long.valueOf(ID), parsedCommand.getOuterId());
    }
}
