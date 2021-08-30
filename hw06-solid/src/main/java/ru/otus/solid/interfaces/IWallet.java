package ru.otus.solid.interfaces;

public interface IWallet {

    int getBalance();

    void put(int sum);

    void take(int sum);
}
