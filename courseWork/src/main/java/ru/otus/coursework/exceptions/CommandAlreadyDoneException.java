package ru.otus.coursework.exceptions;

public class CommandAlreadyDoneException extends RuntimeException {
    public CommandAlreadyDoneException(String command) {
        super("Команда уже выполнена: " + command);
    }
}
