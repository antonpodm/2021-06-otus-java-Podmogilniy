package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Email;
import ru.otus.crm.repository.EmailRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceEmailImpl implements DBServiceEmail {

    private static final Logger log = LoggerFactory.getLogger(DBServiceEmailImpl.class);
    private final TransactionManager transactionManager;
    private final EmailRepository emailRepository;

    @Override
    public List<Email> findAll() {
        var emails = emailRepository.findAll();
        log.info("emails list:{}", emails);
        return emails;
    }

    @Override
    public List<Email> findByProfileId(Long profileId) {
        var emails = emailRepository.findByProfileId(profileId);
        log.info("profile emails list:{}", emails);
        return emails;
    }

    @Override
    public Optional<Email> findById(Long id) {
        var email = emailRepository.findById(id);
        log.info("email by id:{}", email);
        return email;
    }

    @Override
    public Email save(Email email) {
        return transactionManager.doInTransaction(() -> {
            var savedEmail = emailRepository.save(email);
            log.info("saved email: {}", savedEmail);
            return savedEmail;
        });
    }

    @Override
    public void delete(Email email) {
        emailRepository.delete(email);
        log.info("deleted email: {}", email);
    }

    @Override
    public void deleteById(Long id) {
        emailRepository.deleteById(id);
        log.info("deleted email: {}", id);
    }
}
