package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Good;

import java.util.List;

public interface GoodRepository extends CrudRepository<Good, Long> {

    List<Good> findByProfileId(Long profileId);

    @Override
    List<Good> findAll();

}
