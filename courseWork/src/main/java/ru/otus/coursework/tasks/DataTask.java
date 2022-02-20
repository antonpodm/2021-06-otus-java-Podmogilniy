package ru.otus.coursework.tasks;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.coursework.services.LoadDataService;
import ru.otus.coursework.services.SendDataService;

@Component
@RequiredArgsConstructor
public class DataTask {

    private static final Logger log = LoggerFactory.getLogger(DataTask.class);

    private final LoadDataService htmlParserService;
    private final SendDataService sendDataService;

    @Scheduled(fixedRateString = "${app.checkTime}")
    public void checkData() {
        log.info("Starting check data");
        loadData();
        sendData();
        log.info("Check data done");
    }

    private void loadData() {
        try {
            htmlParserService.loadData();
        } catch (Exception ex) {
            log.info("loadData", ex);
        }
    }

    private void sendData() {
        try {
            sendDataService.prepareGoodsDataAndSend();
        } catch (Exception ex) {
            log.info("sendData", ex);
        }
    }
}
