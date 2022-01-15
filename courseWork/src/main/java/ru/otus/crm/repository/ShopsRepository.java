package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Shop;

import java.util.List;

public interface ShopsRepository extends CrudRepository<Shop, Long> {

    @Override
    List<Shop> findAll();

    List<Shop> findByGoodInfoId(Long goodInfoId);
}
