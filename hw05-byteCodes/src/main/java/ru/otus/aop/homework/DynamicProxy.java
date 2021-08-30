package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.ITestLogging;

public class DynamicProxy {

    public static void main(String[] args) {

        var testLogging = (ITestLogging) Ioc.createMyClass(new TestLogging());

        System.out.println(testLogging.calculation(100));
        System.out.println(testLogging.calculation(100, 50));
        System.out.println(testLogging.calculation(100, 50, 7));
    }
}
