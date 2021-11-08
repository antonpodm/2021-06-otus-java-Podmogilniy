package ru.otus.jdbc.mapper;

public class RowDataMapperException extends RuntimeException {

    RowDataMapperException(String message) {
        super(message);
    }

    RowDataMapperException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
