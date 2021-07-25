package ru.otus.test.tester;

public class VerifyException extends Exception {
    private String exceptionMessage = "Ошибка проверки";

    public VerifyException() {

    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
