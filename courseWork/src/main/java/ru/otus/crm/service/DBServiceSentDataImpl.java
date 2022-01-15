package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.SentData;
import ru.otus.crm.repository.SentDataRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceSentDataImpl implements DBServiceSentData {

    private static final Logger log = LoggerFactory.getLogger(DBServiceShopsImpl.class);

    private final TransactionManager transactionManager;
    private final SentDataRepository sentDataRepository;

    @Override
    public List<SentData> findAll() {
        var datas = sentDataRepository.findAll();
        log.info("sent data:{}", datas);
        return datas;
    }

    @Override
    public Optional<SentData> findByUserIdLimit1(Long userId) {
        var data = sentDataRepository.findByUserIdLimit1(userId);
        log.info("sent data {}", data);
        return data;
    }

    @Override
    public SentData save(SentData sentData) {
        return transactionManager.doInTransaction(() -> {
            try {
                var savedSentData = sentDataRepository.save(sentData);
                log.info("saved sent data: {}", savedSentData);
                return savedSentData;
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
            return null;
        });
    }

    @Override
    public void delete(SentData sentData) {
        sentDataRepository.delete(sentData);
        log.info("deleted data: {}", sentData);
    }

    @Override
    public void deleteById(Long id) {
        sentDataRepository.deleteById(id);
        log.info("deleted data: {}", id);
    }
}
