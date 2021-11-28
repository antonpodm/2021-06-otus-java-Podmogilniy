package ru.otus.appcontainer.api;

public class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(Throwable ex) {
        super(ex);
    }
}
