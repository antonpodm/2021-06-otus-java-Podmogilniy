package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.cachehw.MyKey;
import ru.otus.cachehw.MyKeyImpl;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.Client;
import ru.otus.core.sessionmanager.TransactionRunner;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionRunner transactionRunner;
    private final MyCache<MyKey<Long>, Client> cache;

    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> clientDataTemplate, MyCache<MyKey<Long>, Client> cache) {
        this.transactionRunner = transactionRunner;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }
    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> clientDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = null;
    }

    @Override
    public Client saveClient(Client client) {
        Client savedClient = transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return client;
        });
        if (cache != null) {
            cache.put(new MyKeyImpl<Long>(savedClient.getId()), savedClient);
        }
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client foundClient = null;

        if (cache != null) {
            foundClient = cache.get(new MyKeyImpl<Long>(id));
        }
        if (foundClient == null) {
            var clientFromDB = transactionRunner.doInTransaction(connection -> {
                var clientOptional = clientDataTemplate.findById(connection, id);
                log.info("client: {}", clientOptional);
                return clientOptional;
            });
            if (cache != null && clientFromDB.isPresent()) {
                cache.put(new MyKeyImpl<Long>(clientFromDB.get().getId()), clientFromDB.get());
            }
            return clientFromDB;
        } else {
            return Optional.of(foundClient);
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
