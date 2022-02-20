package ru.otus.coursework.exceptions;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String message, Throwable ex) {
        super("Сообщение не отправлено: " + message, ex);
    }
}
