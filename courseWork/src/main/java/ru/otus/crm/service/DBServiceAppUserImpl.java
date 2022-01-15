package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.AppUser;
import ru.otus.crm.repository.AppUserRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceAppUserImpl implements DBServiceAppUser {
    private static final Logger log = LoggerFactory.getLogger(DBServiceAppUserImpl.class);
    private final TransactionManager transactionManager;
    private final AppUserRepository appUserRepository;

    @Override
    public List<AppUser> findAll() {
        var appUsers = appUserRepository.findAll();
        log.info("users:{}", appUsers);
        return appUsers;
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        var user = appUserRepository.findById(id);
        log.info("user: {}", user);
        return user;
    }

    @Override
    public List<AppUser> findByActive(boolean active) {
        var users = appUserRepository.findByIsActive(active);
        log.info("users by active:{}", users);
        return users;
    }

    @Override
    public Optional<AppUser> findByTelegramId(Long telegramId) {
        var user = appUserRepository.findByTelegramId(telegramId);
        log.info("user: {}", user);
        return user;
    }

    @Override
    public AppUser save(AppUser appUser) {
        return transactionManager.doInTransaction(() -> {
            try {
                var savedUser = appUserRepository.save(appUser);
                log.info("saved user: {}", savedUser);
                return savedUser;
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
            return null;
        });
    }

    @Override
    public void delete(AppUser appUser) {
        appUserRepository.delete(appUser);
        log.info("deleted user: {}", appUser);
    }

    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
        log.info("deleted user: {}", id);
    }
}
