package ru.otus.solid.interfaces;

public interface IWallet {

    int getAmount();

    void put(int sum);

    void take(int sum);
}
