package ru.otus.test;


import ru.otus.homework.Homework;
import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.test.tester.Tester;
import ru.otus.test.tester.VerifyException;

public class HomeworkTest {

    private Homework homework;

    @Before
    public void setUp() {
        System.out.println("Before test");
        homework = new Homework();
    }

    @After
    public void tearDown() {
        System.out.println("After test");
        homework = null;
    }

    @Test
    public void multipleByTwoTest() throws VerifyException {
        Long number = 20L;
        number = homework.multipleByTwo(number);
        Tester.verify(40L, number);
    }

    @Test
    public void divideByTwoLongParamTest() throws VerifyException {
        Long number = 20L;
        Tester.verify(10L, homework.divideByTwo(number));
    }

    @Test
    public void divideByTwoStringParamTest() throws VerifyException {
        String number = "20L";
        Tester.verify(10L, homework.divideByTwo(number));
    }

    @Test
    public void getSomethingTest() throws VerifyException {
        Tester.verify("", homework.getSomething());
    }
}
