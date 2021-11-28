package ru.otus.core.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;

import static org.assertj.core.api.Assertions.assertThat;

class DataTemplateHibernateTest extends AbstractHibernateTest {

    @Test
    @DisplayName(" корректно сохраняет, изменяет и загружает клиента по заданному id")
    void shouldSaveAndFindCorrectClientById() {
        //given
        var client = Client.builder().setName("Вася").setAddress(new Address("Address1")).build();

        //when
        var savedClient = transactionManager.doInTransaction(session -> {
            clientTemplate.insert(session, client);
            return client;
        });

        //then
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());

        //when
        var loadedSavedClient = transactionManager.doInTransaction(session ->
                clientTemplate.findById(session, savedClient.getId())
        );

        //then
        assertThat(loadedSavedClient).isPresent().get().usingRecursiveComparison().isEqualTo(savedClient);

        //when
        var updatedClient = savedClient.clone();
        updatedClient.setName("updatedName");
        transactionManager.doInTransaction(session -> {
            clientTemplate.update(session, updatedClient);
            return null;
        });

        //then
        var loadedClient = transactionManager.doInTransaction(session ->
                clientTemplate.findById(session, updatedClient.getId())
        );
        assertThat(loadedClient).isPresent().get().usingRecursiveComparison().isEqualTo(updatedClient);

        //when
        var clientList = transactionManager.doInTransaction(session ->
                clientTemplate.findAll(session)
        );

        //then
        assertThat(clientList.size()).isEqualTo(3);
        assertThat(clientList.get(2)).usingRecursiveComparison().isEqualTo(updatedClient);
    }
}
