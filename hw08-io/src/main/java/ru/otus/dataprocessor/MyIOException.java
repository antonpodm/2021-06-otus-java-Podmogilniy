package ru.otus.dataprocessor;

public class MyIOException extends RuntimeException{

    public MyIOException(){
        super();
    }

    public MyIOException(String message){
        super(message);
    }

}
