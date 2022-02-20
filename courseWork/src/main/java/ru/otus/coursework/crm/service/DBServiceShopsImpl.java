package ru.otus.coursework.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.coursework.crm.model.Shop;
import ru.otus.coursework.crm.repository.ShopsRepository;
import ru.otus.coursework.sessionmanager.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServiceShopsImpl implements DBServiceShops {

    private static final Logger log = LoggerFactory.getLogger(DBServiceShopsImpl.class);

    private final TransactionManager transactionManager;
    private final ShopsRepository shopsRepository;

    @Override
    public List<Shop> findAll() {
        var shops = shopsRepository.findAll();
        log.info("shops list:{}", shops);
        return shops;
    }

    @Override
    public List<Shop> findByGoodInfoId(Long goodInfoId) {
        var shops = shopsRepository.findByGoodInfoId(goodInfoId);
        log.info("goods info shops {}", shops);
        return shops;
    }

    @Override
    public Shop save(Shop shop) {
        return transactionManager.doInTransaction(() -> {
            try {
                var savedShop = shopsRepository.save(shop);
                log.info("saved shop: {}", savedShop);
                return savedShop;
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
            return null;
        });
    }

    @Override
    public void delete(Shop shop) {
        shopsRepository.delete(shop);
        log.info("deleted shop: {}", shop);
    }

    @Override
    public void deleteById(Long id) {
        shopsRepository.deleteById(id);
        log.info("deleted shop: {}", id);
    }
}
