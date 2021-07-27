package ru.otus.homework;

public class Homework {

    public Long multipleByTwo(Long number) {
        return number * 2;
    }

    public Long divideByTwo(Object number) {
        return (Long) number / 2;
    }

    public String getSomething() {
        throw new UnsupportedOperationException();
    }
}
