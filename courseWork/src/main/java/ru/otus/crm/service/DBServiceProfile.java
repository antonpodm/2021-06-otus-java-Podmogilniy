package ru.otus.crm.service;

import ru.otus.crm.model.Profile;

import java.util.List;
import java.util.Optional;

public interface DBServiceProfile {

    List<Profile> findAll();

    Optional<Profile> findById(Long id);

    Profile save(Profile profile);

    void delete(Profile profile);

    void deleteById(Long id);
}
