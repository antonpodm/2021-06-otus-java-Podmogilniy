package ru.otus.coursework.crm.service;

import ru.otus.coursework.crm.model.Good;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DBServiceGood {

    List<Good> findAll();

    List<Good> findByUserId(Long userId);

    Optional<Good> findByUserIdAndOuterId(Long userId, Long outerId);

    Set<Long> findUniqOuterIds();

    Good save(Good good);

    void delete(Good good);

    void deleteById(Long id);
}
