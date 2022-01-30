package ru.otus.coursework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.coursework.crm.model.GoodInfo;

import java.util.List;
import java.util.Optional;

public interface GoodInfoRepository extends CrudRepository<GoodInfo, Long> {

    @Override
    List<GoodInfo> findAll();

    Optional<GoodInfo> findByOuterId(Long outerId);
}
