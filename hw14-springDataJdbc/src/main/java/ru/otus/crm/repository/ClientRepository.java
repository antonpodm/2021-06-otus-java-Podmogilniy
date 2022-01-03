package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Override
    List<Client> findAll();
}
