package ru.otus.dao;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;

public class ClientDaoImpl implements ClientDao{
    private final DBServiceClient dbServiceClient;

    public ClientDaoImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public List<Client> getAll() {
        return dbServiceClient.findAll();
    }

    @Override
    public void saveClient(Client client) {
        dbServiceClient.saveClient(client);
    }
}
