package ru.otus.solid.atm;

import ru.otus.solid.interfaces.Wallet;
import ru.otus.solid.notes.Notes;

import java.util.Map;

public class ATMHelper {

    public static Notes findNote(Map<Notes, Wallet> cash, int sumForTake) {
        return cash.keySet()
                .stream()
                .filter(element -> element.getValue() == sumForTake)
                .findAny()
                .orElse(null);
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isAmountValid(int amount) {
        return amount > 0;
    }
}
