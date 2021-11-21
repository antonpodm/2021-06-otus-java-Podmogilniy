package ru.otus.dao;

import ru.otus.crm.model.Client;

import java.util.List;

public interface ClientDao {

    List<Client> getAll();

    void saveClient(Client client);
}
