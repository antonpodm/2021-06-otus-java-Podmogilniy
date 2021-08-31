package ru.otus.solid;

import ru.otus.solid.atm.ATM;
import ru.otus.solid.exceptions.PutCashException;
import ru.otus.solid.exceptions.TakeCashException;
import ru.otus.solid.interfaces.INote;
import ru.otus.solid.interfaces.IWallet;
import ru.otus.solid.notes.FiftyRubles;
import ru.otus.solid.notes.OneHundredRubles;
import ru.otus.solid.notes.OneThousandRubles;
import ru.otus.solid.wallets.Wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Homework {

    public static void main(String[] args) {
        ATM atm = new ATM(createBaseValues());
        System.out.println("Баланс " + atm.getCashBalance());
        try {
            atm.putCash(50);
            System.out.println("Баланс " + atm.getCashBalance());
        } catch (PutCashException ex) {
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

    private static Map<INote, IWallet> createBaseValues() {
        var startingValues = new ConcurrentHashMap<INote, IWallet>();
        var fiftyRubles = new FiftyRubles();
        var fiftyRublesWallet = new Wallet(10);

        var oneHundredRubles = new OneHundredRubles();
        var oneHundredRublesWallet = new Wallet(5);

        var oneThousandRubles = new OneThousandRubles();
        var oneThousandRublesWallet = new Wallet(1);

        startingValues.put(fiftyRubles, fiftyRublesWallet);
        startingValues.put(oneHundredRubles, oneHundredRublesWallet);
        startingValues.put(oneThousandRubles, oneThousandRublesWallet);

        return startingValues;
    }
}
