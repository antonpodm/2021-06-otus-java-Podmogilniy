package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Good;
import ru.otus.crm.repository.GoodRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServiceGoodImpl implements DBServiceGood{

    private static final Logger log = LoggerFactory.getLogger(DBServiceGoodImpl.class);
    private final TransactionManager transactionManager;
    private final GoodRepository goodRepository;

    @Override
    public List<Good> findAll() {
        var goods = goodRepository.findAll();
        log.info("goods list:{}", goods);
        return goods;
    }

    @Override
    public List<Good> findByProfileId(Long profileId) {
        var goods = goodRepository.findByProfileId(profileId);
        log.info("profile goods:{}", goods);
        return goods;
    }

    @Override
    public Good save(Good good) {
        return transactionManager.doInTransaction(() -> {
            var savedGood = goodRepository.save(good);
            log.info("saved good: {}", savedGood);
            return savedGood;
        });
    }

    @Override
    public void delete(Good good) {
        goodRepository.delete(good);
        log.info("deleted good: {}", good);
    }

    @Override
    public void deleteById(Long id) {
        goodRepository.deleteById(id);
        log.info("deleted good: {}", id);
    }
}
