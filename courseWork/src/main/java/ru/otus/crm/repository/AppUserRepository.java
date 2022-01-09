package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    @Override
    List<AppUser> findAll();

    Optional<AppUser> findByTelegramId(Long telegramId);
}
