package ru.otus.solid.interfaces;

public interface Wallet {

    int getAmount();

    void put(int sum);

    void take(int sum);
}
