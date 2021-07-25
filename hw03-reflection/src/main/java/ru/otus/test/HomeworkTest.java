package ru.otus.test;


import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class HomeworkTest {
    @Before
    public void setUp() {
        System.out.println("Before test");
    }

    @After
    public void tearDown() {
        System.out.println("After test");
    }

    @Test
    public void divideByTwoTest() {

    }

    @Test
    public void multipleByTwoLongParamTest() {

    }

    @Test
    public void multipleByTwoStringParamTest() {

    }

    @Test
    public void getSomethingTest() {

    }
}
