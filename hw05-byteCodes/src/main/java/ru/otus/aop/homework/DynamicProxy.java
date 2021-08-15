package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.ITestLogging;

public class DynamicProxy {

    public static void main(String[] args) {

        var testLogging = (ITestLogging) Ioc.createMyClass(new TestLogging());

        testLogging.calculation(100);
        testLogging.calculation(100, 50);
        testLogging.calculation(100, 50, 5);
    }
}
