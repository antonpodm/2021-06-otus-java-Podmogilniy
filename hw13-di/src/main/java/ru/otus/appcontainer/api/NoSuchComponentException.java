package ru.otus.appcontainer.api;

public class NoSuchComponentException extends RuntimeException{

    public NoSuchComponentException(String name){
        super(name);
    }
}
