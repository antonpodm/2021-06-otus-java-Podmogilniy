package ru.otus.solid;

import ru.otus.solid.atm.ATMImpl;
import ru.otus.solid.exceptions.PutCashException;
import ru.otus.solid.exceptions.TakeCashException;
import ru.otus.solid.interfaces.Wallet;
import ru.otus.solid.notes.Notes;
import ru.otus.solid.wallets.WalletImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Homework {

    public static void main(String[] args) {
        var atm = new ATMImpl(createBaseValues());
        System.out.println("Баланс " + atm.getCashBalance());
        try {
            atm.takeCash(290);
            System.out.println("Баланс " + atm.getCashBalance());
        } catch (TakeCashException ex) {
            System.out.println(ex);
        }
        try {
            atm.putCash(10);
            System.out.println("Баланс " + atm.getCashBalance());
        } catch (PutCashException ex) {
            System.out.println(ex);
        }
        try {
            atm.takeCash(10);
            System.out.println("Баланс " + atm.getCashBalance());
        } catch (TakeCashException ex) {
            System.out.println(ex);
        }
        try {
            atm.takeCash(1000);
            System.out.println("Баланс " + atm.getCashBalance());
        } catch (TakeCashException ex) {
            System.out.println(ex);
        }
    }

    private static Map<Notes, Wallet> createBaseValues() {
        var startingValues = new ConcurrentHashMap<Notes, Wallet>();
        var eightyRubles = Notes.EightyRubles;
        var eightyRublesWallet = new WalletImpl(1);

        var oneHundredRubles = Notes.OneHundredRubles;
        var oneHundredRublesWallet = new WalletImpl(2);

        var fortyRubles = Notes.FortyRubles;
        var fortyRublesWallet = new WalletImpl(3);

        var thirtyRubles = Notes.ThirtyRubles;
        var thirtyRublesWallet = new WalletImpl(4);

        startingValues.put(eightyRubles, eightyRublesWallet);
        startingValues.put(oneHundredRubles, oneHundredRublesWallet);
        startingValues.put(fortyRubles, fortyRublesWallet);
        startingValues.put(thirtyRubles, thirtyRublesWallet);

        return startingValues;
    }
}
