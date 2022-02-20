package ru.otus.coursework.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.coursework.crm.model.GoodInfo;
import ru.otus.coursework.crm.repository.GoodInfoRepository;
import ru.otus.coursework.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceGoodInfoImpl implements DBServiceGoodInfo {

    private static final Logger log = LoggerFactory.getLogger(DBServiceGoodInfoImpl.class);

    private final TransactionManager transactionManager;
    private final GoodInfoRepository goodInfoRepository;

    @Override
    public List<GoodInfo> findAll() {
        var goodsInfo = goodInfoRepository.findAll();
        log.info("goods info list:{}", goodsInfo);
        return goodsInfo;
    }

    @Override
    public Optional<GoodInfo> findByOuterId(Long outerId) {
        var goodsInfo = goodInfoRepository.findByOuterId(outerId);
        log.info(" outerId goods info {}", goodsInfo);
        return goodsInfo;
    }

    @Override
    public GoodInfo save(GoodInfo goodInfo) {
        return transactionManager.doInTransaction(() -> {
            try {
                var savedGoodInfo = goodInfoRepository.save(goodInfo);
                log.info("saved good info: {}", savedGoodInfo);
                return savedGoodInfo;
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
            return null;
        });
    }

    @Override
    public void delete(GoodInfo goodInfo) {
        goodInfoRepository.delete(goodInfo);
        log.info("deleted good info: {}", goodInfo);
    }

    @Override
    public void deleteById(Long id) {
        goodInfoRepository.deleteById(id);
        log.info("deleted good info: {}", id);
    }
}
