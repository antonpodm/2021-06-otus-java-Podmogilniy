package ru.otus.solid.exceptions;

public class PutCashException extends MyException{

    private String exceptionMessage = "Банкомат не поддерживает купюры такого типа";

    public PutCashException() {
    }

    public PutCashException(String msg) {
        exceptionMessage += ": " + msg;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
