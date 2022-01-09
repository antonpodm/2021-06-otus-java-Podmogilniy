package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends CrudRepository<Good, Long> {

    List<Good> findByUserId(Long userId);

    Optional<Good> findByUserIdAndOuterId(Long userId, Long outerId);

    @Override
    List<Good> findAll();

}
