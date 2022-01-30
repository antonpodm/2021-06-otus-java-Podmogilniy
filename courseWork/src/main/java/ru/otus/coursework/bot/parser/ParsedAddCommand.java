package ru.otus.coursework.bot.parser;

import lombok.Getter;
import ru.otus.coursework.dto.enums.Commands;
import ru.otus.coursework.dto.enums.DealType;
import ru.otus.coursework.dto.enums.MathStatement;

@Getter
public class ParsedAddCommand extends ParsedCommand {

    private final int PARAMS_AMOUNT = 5;
    private final String COMMAND_NAME = Commands.ADD_GOOD.getCommand();
    private String name;
    private Long outerId;
    private DealType dealType;
    private MathStatement mathStatement;
    private Long price;

    public ParsedAddCommand(String[] strings) {
        checkParamsAmount(strings, PARAMS_AMOUNT, COMMAND_NAME);
        addName(strings);
        addOuterId(strings);
        addDealType(strings);
        addMathStatement(strings);
        addPrice(strings);
    }

    private void addName(String[] strings) {
        if (!strings[0].isEmpty()) {
            name = strings[0];
        } else {
            makeException(COMMAND_NAME);
        }
    }

    private void addOuterId(String[] strings) {
        try {
            outerId = Long.valueOf(strings[1]);
        } catch (Exception ex) {
            makeException(COMMAND_NAME);
        }
    }

    private void addDealType(String[] strings) {
        try {
            var userDealType = strings[2].toUpperCase();
            if (userDealType.contains("ПО") || userDealType.contains("КУ") || userDealType.contains("BUY")) {
                dealType = DealType.BUY;
            } else if (userDealType.contains("ПР") || userDealType.contains("SELL")) {
                dealType = DealType.SELL;
            } else {
                makeException(COMMAND_NAME);
            }
        } catch (Exception ex) {
            makeException(COMMAND_NAME);
        }
    }

    private void addMathStatement(String[] strings) {
        try {
            var userStatement = strings[3].toUpperCase();
            if (userStatement.contains("Б") || userStatement.contains("OR")) {
                mathStatement = MathStatement.MORE;
            } else if (userStatement.contains("МЕ") || userStatement.contains("ЕНЬ")|| userStatement.contains("ES")) {
                mathStatement = MathStatement.LESS;
            } else {
                makeException(COMMAND_NAME);
            }
        } catch (Exception ex) {
            makeException(COMMAND_NAME);
        }
    }

    private void addPrice(String[] strings) {
        try {
            price = Long.valueOf(strings[4]);
        } catch (Exception ex) {
            makeException(COMMAND_NAME);
        }
    }

}
