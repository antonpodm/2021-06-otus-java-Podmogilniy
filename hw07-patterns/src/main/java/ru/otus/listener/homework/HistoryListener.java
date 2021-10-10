package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> historyMessages = new ConcurrentHashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message savedMessage = msg.clone();
        addMessage(savedMessage.getId(), savedMessage);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(getMessage(id));
    }


    public Message getMessage(long id) {
        return historyMessages.get(id);
    }

    public void addMessage(long id, Message message) {
        historyMessages.put(id, message);
    }

    public void removeMessage(long id) {
        historyMessages.remove(id);
    }
}
