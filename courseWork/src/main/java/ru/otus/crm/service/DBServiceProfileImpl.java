package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Profile;
import ru.otus.crm.repository.ProfileRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceProfileImpl implements DBServiceProfile {
    private static final Logger log = LoggerFactory.getLogger(DBServiceProfileImpl.class);
    private final TransactionManager transactionManager;
    private final ProfileRepository profileRepository;

    @Override
    public List<Profile> findAll() {
        var profileList = profileRepository.findAll();
        log.info("profileList:{}", profileList);
        return profileList;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        var profile = profileRepository.findById(id);
        log.info("profile: {}", profile);
        return profile;
    }

    @Override
    public Profile save(Profile profile) {
        return transactionManager.doInTransaction(() -> {
            var savedProfile = profileRepository.save(profile);
            log.info("saved profile: {}", savedProfile);
            return savedProfile;
        });
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
        log.info("deleted profile: {}", profile);
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.deleteById(id);
        log.info("deleted profile: {}", id);
    }
}
