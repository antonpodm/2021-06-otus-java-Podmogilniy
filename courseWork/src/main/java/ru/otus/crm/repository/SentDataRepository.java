package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.crm.model.SentData;

import java.util.List;
import java.util.Optional;

public interface SentDataRepository extends CrudRepository<SentData, Long> {

    @Override
    List<SentData> findAll();

    @Query("SELECT * FROM sent_data sd WHERE user_id = :userId ORDER BY id DESC LIMIT 1")
    Optional<SentData> findByUserIdLimit1(@Param("userId") Long userId);

}
