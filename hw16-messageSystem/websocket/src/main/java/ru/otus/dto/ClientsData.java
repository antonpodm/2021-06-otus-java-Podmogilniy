package ru.otus.dto;

import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ClientsData implements ResultDataType {
    private final List<Client> clients;

    public ClientsData(Client client) {
        this.clients = List.of(client);
    }

    public ClientsData(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<ClientDto> getClientsDto() {
        return clients
                .stream()
                .map(ClientDto::new)
                .toList();
    }

    public Client getFirstClient() {
        return clients
                .stream()
                .findFirst()
                .orElse(null);
    }
}
