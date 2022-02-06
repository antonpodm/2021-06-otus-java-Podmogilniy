package ru.otus.coursework.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.coursework.bot.parser.ParsedAddCommand;
import ru.otus.coursework.enums.DealType;
import ru.otus.coursework.enums.MathStatement;
import ru.otus.coursework.exceptions.CommandFormatException;

import java.util.Arrays;

public class ParsedAddCommandTest {

    private static final String NAME = "seed";
    private static final String ID = "608";
    private static final String DEAL_TYPE = "Продажа";
    private static final String MATH_STATEMENT = "меньше";
    private static final String PRICE = "25000";
    private static final String[] rightFormatCommand = {
            NAME,
            ID,
            DEAL_TYPE,
            MATH_STATEMENT,
            PRICE
    };

    @Test
    void shouldParseCommandWithGoodFormat() {
        var parsedCommand = new ParsedAddCommand(rightFormatCommand);
        Assertions.assertEquals(NAME, parsedCommand.getName());
        Assertions.assertEquals(Long.valueOf(ID), parsedCommand.getOuterId());
        Assertions.assertEquals(DealType.findByDescription(DEAL_TYPE).get(), parsedCommand.getDealType());
        Assertions.assertEquals(MathStatement.findByDescription(MATH_STATEMENT).get(), parsedCommand.getMathStatement());
        Assertions.assertEquals(Long.valueOf(PRICE), parsedCommand.getPrice());
    }

    @Test
    void shouldThrowCommandFormatExceptionOnWrongIdParam() {
        var id = "qwerty";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[1] = id;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnWrongDealTypeParam() {
        var type = "1";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[2] = type;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
        type = "пакпка";
        command[2] = type;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
    }

    @Test
    void shouldParseDifferentDealTypes() {
        var type = "покупка";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[2] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "купить";
        command[2] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "buy";
        command[2] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "sell";
        command[2] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "продажа";
        command[2] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnWrongMathStatementParam() {
        var statement = "1";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[3] = statement;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
        statement = "млеше";
        command[3] = statement;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
        statement = "льше";
        command[3] = statement;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
    }

    @Test
    void shouldParseDifferentMathStatements() {
        var type = "больше";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[3] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "меньше";
        command[3] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "more";
        command[3] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
        type = "less";
        command[3] = type;
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnWrongPriceParam() {
        var price = "qwerty";
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length);
        command[4] = price;
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
    }

    @Test
    void shouldThrowCommandFormatExceptionOnEmptyParams() {
        var command = new String[1];
        Assertions.assertThrows(CommandFormatException.class, () -> new ParsedAddCommand(command));
    }

    @Test
    void shouldParseCommandOnMoreThanFiveParams() {
        var command = Arrays.copyOf(rightFormatCommand, rightFormatCommand.length+1);
        command[5] = "param";
        Assertions.assertDoesNotThrow(() -> new ParsedAddCommand(command));
    }
}
