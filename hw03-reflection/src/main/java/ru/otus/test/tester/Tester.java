package ru.otus.test.tester;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();
    private List<Method> testMethods = new ArrayList<>();
    private int testAmount = 0;
    private int successTestAmount = 0;
    private int failedTestAmount = 0;

    private final Class testedClass;

    public Tester(Class<?> testedClass) {
        this.testedClass = testedClass;
    }

    public void test() {
        Method[] methods = testedClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        for (Method testMethod : testMethods) {
            System.out.println("Running " + testMethod.getName());
            Object instance = ReflectionHelper.instantiate(testedClass);
            testAmount++;
            try {
                beforeMethods.forEach(method -> ReflectionHelper.callMethod(instance, method.getName()));
                ReflectionHelper.callMethod(instance, testMethod.getName());
                successTestAmount++;
                System.out.println("Succeed " + testMethod.getName());
            } catch (Exception ex) {
                System.out.println("Failed " + testMethod.getName());
                failedTestAmount++;
            } finally {
                afterMethods.forEach(method -> ReflectionHelper.callMethod(instance, method.getName()));
                System.out.println();
            }
        }
        System.out.println(makeResult());
        clear();
    }

    private void clear() {
        beforeMethods.clear();
        afterMethods.clear();
        testMethods.clear();
        testAmount = 0;
        failedTestAmount = 0;
        successTestAmount = 0;
    }

    private String makeResult() {
        StringBuilder builder = new StringBuilder();
        builder.append("Выполнено тестов: ")
                .append(testAmount)
                .append(", Успешно: ")
                .append(successTestAmount)
                .append(", Провалено: ")
                .append(failedTestAmount);
        return builder.toString();
    }

    public static void verify(Object expected, Object result) throws VerifyException {
        if (!objectsEquals(expected, result)) {
            throw new VerifyException();
        }
    }

    private static boolean objectsEquals(Object expected, Object result) {
        if (expected == null) {
            return (result == null);
        }
        return expected.equals(result);
    }
}
