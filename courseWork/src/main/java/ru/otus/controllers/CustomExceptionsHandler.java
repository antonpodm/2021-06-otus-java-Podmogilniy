package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.exceptions.EmailNotFoundException;
import ru.otus.exceptions.GoodNotFoundException;
import ru.otus.exceptions.ProfileNotFoundException;

@ControllerAdvice
public class CustomExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String emailNotFoundHandler(EmailNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String goodNotFoundHandler(GoodNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String profileNotFoundHandler(ProfileNotFoundException ex) {
        return ex.getMessage();
    }
}
