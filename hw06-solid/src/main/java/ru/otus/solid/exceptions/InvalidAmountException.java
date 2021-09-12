package ru.otus.solid.exceptions;

public class InvalidAmountException extends MyException {
    private String exceptionMessage = "Передано не верное количество денег";

    public InvalidAmountException() {
    }

    public InvalidAmountException(String msg) {
        exceptionMessage += ": " + msg;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
