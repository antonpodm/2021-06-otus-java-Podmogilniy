package ru.otus.exceptions;

public class AppUserNotFoundException extends RuntimeException{

    public AppUserNotFoundException(Long id) {
        super("Пользователь не найден: " + id);
    }
}
