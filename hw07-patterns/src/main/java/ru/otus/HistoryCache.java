package ru.otus;

import ru.otus.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryCache {

    private static final Map<Long, Message> historyMessages = new ConcurrentHashMap<>();

    public static Message getMessage(long id) {
        return historyMessages.get(id);
    }

    public static void addMessage(long id, Message message) {
        historyMessages.put(id, message);
    }

    public static void removeMessage(long id) {
        historyMessages.remove(id);
    }
}
