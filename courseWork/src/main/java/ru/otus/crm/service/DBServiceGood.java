package ru.otus.crm.service;

import ru.otus.crm.model.Good;

import java.util.List;

public interface DBServiceGood {
    List<Good> findAll();

    List<Good> findByProfileId(Long profileId);

    Good save(Good good);

    void delete(Good good);

    void deleteById(Long id);
}
