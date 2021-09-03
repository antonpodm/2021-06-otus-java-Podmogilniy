package ru.otus.solid.atm;

import ru.otus.solid.interfaces.INote;
import ru.otus.solid.interfaces.IWallet;

import java.util.Map;

public class ATMHelper {

    public static INote findNote(Map<INote, IWallet> cash, int sumForTake) {
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
}
