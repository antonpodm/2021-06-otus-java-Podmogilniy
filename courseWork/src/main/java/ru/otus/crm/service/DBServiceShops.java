package ru.otus.crm.service;

import ru.otus.crm.model.Shop;

import java.util.List;

public interface DBServiceShops {

    List<Shop> findAll();

    List<Shop> findByGoodInfoId(Long goodInfoId);

    Shop save(Shop shop);

    void delete(Shop shop);

    void deleteById(Long id);
}
