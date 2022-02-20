package ru.otus.coursework.crm.service;

import ru.otus.coursework.crm.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface DBServiceAppUser {

    List<AppUser> findAll();

    Optional<AppUser> findById(Long id);

    List<AppUser> findByActive(boolean active);

    Optional<AppUser> findByTelegramId(Long telegramId);

    AppUser save(AppUser appUser);

    void delete(AppUser appUser);

    void deleteById(Long id);
}
