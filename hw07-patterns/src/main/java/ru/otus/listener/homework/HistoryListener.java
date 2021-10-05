package ru.otus.listener.homework;

import ru.otus.HistoryCache;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        Message savedMessage = msg.toBuilder().build();
        HistoryCache.addMessage(savedMessage.getId(), savedMessage);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(HistoryCache.getMessage(id));
    }
}
