package ru.otus.solid.wallets;

import ru.otus.solid.interfaces.IWallet;

public class Wallet implements IWallet {

    private int amount = 0;

    public Wallet() {
    }

    public Wallet(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void put(int sum) {
        this.amount += sum;
    }

    @Override
    public void take(int sum) {
        this.amount -= sum;
    }
}
