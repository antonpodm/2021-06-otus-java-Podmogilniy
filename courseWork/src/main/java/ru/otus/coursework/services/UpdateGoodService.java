package ru.otus.coursework.services;

import org.springframework.stereotype.Service;
import ru.otus.coursework.bot.parser.ParsedAddCommand;
import ru.otus.coursework.crm.model.Good;

@Service
public class UpdateGoodService {

    public Good updateFromAddCommand(Good good, ParsedAddCommand parsedAddCommand) {
        return good.toBuilder().outerId(parsedAddCommand.getOuterId())
                .dealType(parsedAddCommand.getDealType())
                .mathStatement(parsedAddCommand.getMathStatement())
                .price(parsedAddCommand.getPrice())
                .name(parsedAddCommand.getName())
                .build();
    }
}
