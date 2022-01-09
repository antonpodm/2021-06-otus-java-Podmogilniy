package ru.otus.exceptions;

public class CommandFormatException extends RuntimeException{
    public CommandFormatException(String command) {
        super("Формат команды не верен: " + command);
    }
}
