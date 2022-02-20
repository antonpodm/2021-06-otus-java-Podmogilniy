package ru.otus.coursework.crm.service;

import ru.otus.coursework.crm.model.GoodInfo;

import java.util.List;
import java.util.Optional;

public interface DBServiceGoodInfo {

    List<GoodInfo> findAll();

    Optional<GoodInfo> findByOuterId(Long outerId);

    GoodInfo save(GoodInfo goodInfo);

    void delete(GoodInfo goodInfo);

    void deleteById(Long id);
}
