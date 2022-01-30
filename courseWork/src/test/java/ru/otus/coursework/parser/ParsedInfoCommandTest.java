package ru.otus.coursework.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.coursework.bot.parser.ParsedInfoCommand;
import ru.otus.coursework.exceptions.CommandFormatException;

public class ParsedInfoCommandTest {

    @Test
    void shouldParseCommandSingleNumberParam() {
        var id = 607L;
        var command = new String[1];
        command[0] = String.valueOf(id);
        var parsedCommand = new ParsedInfoCommand(command);
        Assertions.assertEquals(id, parsedCommand.getOuterId());
    }

    @Test
    void shouldThrowCommandFormatExceptionOnStringParam() {
        var id = "qwerty";
        var command = new String[1];
        command[0] = id;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedInfoCommand(command));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnEmptyParams() {
        var command = new String[1];
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedInfoCommand(command));
    }

    @Test
    void shouldParseCommandOnMoreThanOneParam() {
        var id = 607L;
        var command = new String[2];
        command[0] = String.valueOf(id);
        command[1] = "param";
        var parsedCommand = new ParsedInfoCommand(command);
        Assertions.assertEquals(id, parsedCommand.getOuterId());
    }
}
