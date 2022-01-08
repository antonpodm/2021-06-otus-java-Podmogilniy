package ru.otus.exceptions;

public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(Long id) {
        super("Профиль не найден: " + id);
    }
}
