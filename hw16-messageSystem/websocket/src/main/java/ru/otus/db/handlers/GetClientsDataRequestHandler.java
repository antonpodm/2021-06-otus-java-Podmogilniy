package ru.otus.db.handlers;

import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;

import java.util.Optional;

public class GetClientsDataRequestHandler implements RequestHandler {

    private final DBServiceClient dbServiceClient;

    public GetClientsDataRequestHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) new ClientsData(dbServiceClient.findAll())));
    }
}