package ru.otus.tasks;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.services.HtmlParserService;
import ru.otus.crm.service.DBServiceGood;
import ru.otus.services.SendDataService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class CheckDataTask {

    private static final Logger log = LoggerFactory.getLogger(CheckDataTask.class);
    private static final Executor threadPool = Executors.newFixedThreadPool(20);
    private final DBServiceGood dbServiceGood;
    private final HtmlParserService htmlParserService;
    private final SendDataService sendDataService;


    @Scheduled(fixedRateString = "${app.checkTime}")
    public void checkData() {
        log.info("Starting check data");
        try {
            var uniqIds = dbServiceGood.findUniqOuterIds();
            var latch = new CountDownLatch(uniqIds.size());
            uniqIds.forEach(id -> {
                CompletableFuture.runAsync(() -> {
                    try {
                        htmlParserService.loadGoodData(id);
                    } catch (Exception ex) {
                        log.info("load data of {} ", id, ex);
                    } finally {
                        latch.countDown();
                    }
                }, threadPool);
            });
            latch.await();
            sendDataService.prepareGoodsDataAndSend();
        } catch (Exception ex) {
            log.info("checkData", ex);
        }
        log.info("Check data done");
    }
}
