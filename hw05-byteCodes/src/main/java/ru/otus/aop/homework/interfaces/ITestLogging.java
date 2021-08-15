package ru.otus.aop.homework.interfaces;

public interface ITestLogging {

    @Log
    int calculation(int param1);

    @Log
    int calculation(int param1, int param2);

    @Log
    int calculation(int param1, int param2, int param3);
}
