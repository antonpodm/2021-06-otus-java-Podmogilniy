package ru.otus.crm.service;

import ru.otus.crm.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface DBServiceAppUser {

    List<AppUser> findAll();

    Optional<AppUser> findById(Long id);

    Optional<AppUser> findByTelegramId(Long telegramId);

    AppUser save(AppUser appUser);

    void delete(AppUser appUser);

    void deleteById(Long id);
}
