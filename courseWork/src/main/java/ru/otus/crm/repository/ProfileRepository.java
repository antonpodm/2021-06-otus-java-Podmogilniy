package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Profile;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @Override
    List<Profile> findAll();
}
