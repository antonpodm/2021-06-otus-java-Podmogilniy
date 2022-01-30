package ru.otus.coursework.exceptions;

public class AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(Long id) {
        super("Пользователь не найден: " + id);
    }
}
