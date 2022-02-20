package ru.otus.coursework.exceptions;

public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(Long id) {
        super("Товар не найден: " + id);
    }
}
