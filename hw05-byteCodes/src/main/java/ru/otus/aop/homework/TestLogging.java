package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.ITestLogging;
import ru.otus.aop.homework.interfaces.Log;

public class TestLogging implements ITestLogging, ILog {

    @Override
    public int calculation(int param1) {
        return param1 * 5;
    }

    @Override
    public int calculation(int param1, int param2) {
        return (param1 + param2) * 5;
    }

    @Override
    public int calculation(int param1, int param2, int param3) {
        return (param1 + param2) * param3;
    }
}
