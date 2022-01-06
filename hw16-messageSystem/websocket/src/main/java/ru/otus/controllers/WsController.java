package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.config.AppConfig;
import ru.otus.config.Properties;
import ru.otus.crm.model.Client;
import ru.otus.dto.ClientDto;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.MessageType;

import java.util.Collections;


@Controller
public class WsController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MsClient databaseMsClient;
    private final MsClient frontendMsClient;
    private final Properties properties;

    public WsController(SimpMessagingTemplate template,
                        @Qualifier(AppConfig.DATABASE_MS_CLIENT) MsClient databaseMsClient,
                        @Qualifier(AppConfig.FRONTEND_MS_CLIENT) MsClient frontendMsClient,
                        Properties properties
    ) {
        this.simpMessagingTemplate = template;
        this.databaseMsClient = databaseMsClient;
        this.frontendMsClient = frontendMsClient;
        this.properties = properties;
    }

    @MessageMapping("/clients")
    public void getClients() {
        var message = frontendMsClient.produceMessage(
                properties.getDatabaseServiceClientName(),
                new ClientsData(Collections.emptyList()),
                MessageType.GET_CLIENTS,
                resultDataType -> simpMessagingTemplate.convertAndSend("/topic/clients", resultDataType.getClientsDto())
        );
        frontendMsClient.sendMessage(message);
    }

    @MessageMapping("/clients/save")
    public void saveClient(ClientDto clientDto) {
        Client client = Client.builder()
                .setName(clientDto.getName())
                .setAddress(clientDto.getAddress())
                .addPhone(clientDto.getPhones().get(0))
                .addPhone(clientDto.getPhones().get(1))
                .build();

        var message = frontendMsClient.produceMessage(
                properties.getDatabaseServiceClientName(),
                new ClientsData(client),
                MessageType.SAVE_CLIENT,
                resultDataType -> simpMessagingTemplate.convertAndSend("/topic/clients/new", resultDataType.getClientsDto())
        );
        frontendMsClient.sendMessage(message);
    }
}
