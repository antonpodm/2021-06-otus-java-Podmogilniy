package ru.otus.solid.exceptions;

public class TakeCashException extends MyException {

    private String exceptionMessage = "Нет возможности выдать запрашиваемую сумму";

    public TakeCashException() {
    }

    public TakeCashException(String msg) {
        exceptionMessage += ": " + msg;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
