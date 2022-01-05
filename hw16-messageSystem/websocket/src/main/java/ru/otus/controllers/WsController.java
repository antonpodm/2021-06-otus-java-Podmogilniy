package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;

import java.security.Principal;
import java.util.List;


@Controller
public class WsController {
    private static final Logger logger = LoggerFactory.getLogger(WsController.class);
    private final DBServiceClient dbServiceClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WsController(DBServiceClient dbServiceClient, SimpMessagingTemplate template) {
        this.dbServiceClient = dbServiceClient;
        this.simpMessagingTemplate = template;
    }

    @MessageMapping("/clients")
    public void getClients(){
        var clients = dbServiceClient
                .findAll()
                .stream()
                .map(ClientDto::new)
                .toList();
        simpMessagingTemplate.convertAndSend("/topic/clients",clients);
    }

    @MessageMapping("/clients/save")
    public void saveClient(ClientDto clientDto) {
        Client client = Client.builder()
                .setName(clientDto.getName())
                .setAddress(clientDto.getAddress())
                .addPhone(clientDto.getPhones().get(0))
                .addPhone(clientDto.getPhones().get(1))
                .build();
        dbServiceClient.saveClient(client);
        var newClientDto = new ClientDto(client);
        simpMessagingTemplate.convertAndSend("/topic/clients/new", List.of(newClientDto));

    }
}
