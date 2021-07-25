package ru.otus.homework;

import ru.otus.test.HomeworkTest;
import ru.otus.test.tester.Tester;

public class Starter {

    public static void main(String[] args){
        Tester tester = new Tester(HomeworkTest.class);
        tester.test();
    }
}
