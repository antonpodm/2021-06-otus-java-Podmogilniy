package ru.otus.coursework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.coursework.crm.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    @Override
    List<AppUser> findAll();

    List<AppUser> findByIsActive(boolean isActive);

    Optional<AppUser> findByTelegramId(Long telegramId);
}
