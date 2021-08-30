package ru.otus.solid;

import ru.otus.solid.interfaces.IWallet;

public class Wallet implements IWallet {

    private int balance = 0;

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void put(int sum) {
        this.balance += sum;
    }

    @Override
    public void take(int sum) {
        this.balance -= sum;
    }
}
