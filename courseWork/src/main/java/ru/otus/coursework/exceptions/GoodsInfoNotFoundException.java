package ru.otus.coursework.exceptions;

public class GoodsInfoNotFoundException extends RuntimeException{
    public GoodsInfoNotFoundException(Long id) {
        super("Информация по товару не найдена: " + id);
    }
}
