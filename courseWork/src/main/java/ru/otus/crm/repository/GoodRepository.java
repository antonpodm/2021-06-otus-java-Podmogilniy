package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Good;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GoodRepository extends CrudRepository<Good, Long> {

    List<Good> findByUserId(Long userId);

    Optional<Good> findByUserIdAndOuterId(Long userId, Long outerId);

    @Override
    List<Good> findAll();

    @Query("SELECT DISTINCT outer_id FROM goods")
    Set<Long> findUniqOuterIds();

}
