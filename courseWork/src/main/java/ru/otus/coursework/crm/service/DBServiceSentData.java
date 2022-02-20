package ru.otus.coursework.crm.service;

import ru.otus.coursework.crm.model.SentData;

import java.util.List;
import java.util.Optional;

public interface DBServiceSentData {
    List<SentData> findAll();

    Optional<SentData> findByUserIdLimit1(Long userId);

    SentData save(SentData sentData);

    void delete(SentData sentData);

    void deleteById(Long id);
}
