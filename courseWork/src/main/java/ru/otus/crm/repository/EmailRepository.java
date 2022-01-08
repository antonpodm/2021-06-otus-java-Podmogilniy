package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Email;

import java.util.List;

public interface EmailRepository extends CrudRepository<Email, Long> {

    @Override
    List<Email> findAll();

    List<Email> findByProfileId(Long profileId);
}
