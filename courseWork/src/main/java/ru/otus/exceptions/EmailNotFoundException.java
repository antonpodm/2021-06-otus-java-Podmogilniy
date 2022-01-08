package ru.otus.exceptions;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Long id) {
        super("Почта не найдена: " + id);
    }
}
