package ru.otus.solid.wallets;

import ru.otus.solid.exceptions.InvalidAmountException;
import ru.otus.solid.interfaces.Wallet;

public class WalletImpl implements Wallet {

    private int amount;

    public WalletImpl() {
        this.amount = 0;
    }

    public WalletImpl(int amount) {
        if (isAmountValid(amount)) {
            this.amount = amount;
        } else {
            throw new InvalidAmountException();
        }
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

    private static boolean isAmountValid(int amount) {
        return amount >= 0;
    }
}
