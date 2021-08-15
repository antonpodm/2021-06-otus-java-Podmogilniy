package ru.otus.aop.homework;

public class DynamicProxy {

    public static void main(String[] args) {

        var testLogging = (TestLogging) Ioc.createMyClass(new TestLogging());

        testLogging.calculation(100);
        testLogging.calculation(100, 50);
        testLogging.calculation(100, 50, 5);
    }
}
