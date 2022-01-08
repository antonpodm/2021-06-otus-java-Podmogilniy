package ru.otus.crm.service;

import ru.otus.crm.model.Email;

import java.util.List;
import java.util.Optional;


public interface DBServiceEmail {

    List<Email> findAll();

    List<Email> findByProfileId(Long profileId);

    Optional<Email> findById(Long id);

    Email save(Email email);

    void delete(Email email);

    void deleteById(Long id);
}

