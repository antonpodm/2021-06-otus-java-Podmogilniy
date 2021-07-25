package ru.otus.test.tester;

import java.lang.reflect.Executable;

public class Tester {

    public static void test(Class testedClass){

    }

    public static boolean verify(Object result, Object testResult){

        return true;
    }

    public boolean assertThrows(Class exception, Executable executable){
        return true;
    }
    
}
