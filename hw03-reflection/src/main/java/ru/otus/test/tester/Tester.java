package ru.otus.test.tester;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
    private int testAmount = 0;
    private int successTestAmount = 0;
    private int failedTestAmount = 0;

    private final Class testedClass;

    public Tester(Class<?> testedClass) {
        this.testedClass = testedClass;
    }

    public void test() {
        findMethods();
        testMethods();
        clear();
    }

    private void findMethods() {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
    }

    private void testMethods() {
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
                failedTestAmount++;
                System.out.println("Failed " + testMethod.getName());
            } finally {
                afterMethods.forEach(method -> ReflectionHelper.callMethod(instance, method.getName()));
                System.out.println();
            }
        }
        System.out.println(makeResult());
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
