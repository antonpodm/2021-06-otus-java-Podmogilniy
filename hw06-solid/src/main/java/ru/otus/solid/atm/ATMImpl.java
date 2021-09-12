package ru.otus.solid.atm;

import ru.otus.solid.exceptions.PutCashException;
import ru.otus.solid.exceptions.TakeCashException;
import ru.otus.solid.interfaces.ATM;
import ru.otus.solid.interfaces.Wallet;
import ru.otus.solid.notes.Notes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.otus.solid.atm.ATMHelper.*;

public class ATMImpl implements ATM {

    private final Map<Notes, Wallet> cash;
    private Integer cashBalance;

    public ATMImpl(Map<Notes, Wallet> cash) {
        this.cash = cash;
        cashBalance = getCashBalance();
    }

    public void putCash(int sumForPut) throws PutCashException {
        if (isAmountValid(sumForPut)) {
            var note = ATMHelper.findNote(cash, sumForPut);
            if (isNotNull(note)) {
                var wallet = cash.get(note);
                if (isNotNull(wallet)) {
                    wallet.put(sumForPut);
                    cashBalance += sumForPut;
                    return;
                }
            }
        }
        throw new PutCashException();
    }

    public Integer getCashBalance() {
        if (isNull(cashBalance)) {
            var balance = 0;
            for (Notes note : cash.keySet()) {
                var wallet = cash.get(note);
                var sum = wallet.getAmount() * note.getValue();
                balance += sum;
            }
            return balance;
        }
        return cashBalance;
    }

    public void takeCash(int sumForTake) throws TakeCashException {
        if (isAmountValid(sumForTake)) {
            var notes = getBestPossibleCombination(sumForTake);
            if (isNotNull(notes)) {
                notes.forEach(note -> {
                    var wallet = cash.get(note);
                    wallet.take(1);
                    cashBalance -= note.getValue();
                });
                return;
            }
        }
        throw new TakeCashException();
    }

    private List<Notes> getBestPossibleCombination(int sumForTake) {
        if (cashBalance < sumForTake) {
            return null;
        }
        var currentMoneyAmount = getDescSortedListOfAllCash();
        var result = new ArrayList<Notes>();
        var indexes = recursiveSearch(currentMoneyAmount, new LinkedList<>(), 0, sumForTake, 0);

        if (!indexes.isEmpty()) {
            indexes.forEach(index -> result.add(currentMoneyAmount.get(index)));
            return result;
        }

        return null;
    }

    private LinkedList<Integer> recursiveSearch(List<Notes> currentMoneyAmount, LinkedList<Integer> indexes, int currentIndex, int sumForTake, int sum) {
        for (; currentIndex < currentMoneyAmount.size(); currentIndex++) {
            if (currentMoneyAmount.get(currentIndex).getValue() + sum == sumForTake) {
                indexes.add(currentIndex);
                break;
            } else if (currentMoneyAmount.get(currentIndex).getValue() + sum < sumForTake) {
                if (currentIndex != currentMoneyAmount.size() - 1) {
                    indexes.add(currentIndex);
                    sum += currentMoneyAmount.get(currentIndex).getValue();
                }
            }
            if (currentIndex == currentMoneyAmount.size() - 1) {
                var lastIndex = indexes.pollLast();
                if (lastIndex == null) {
                    return indexes;
                }
                sum -= currentMoneyAmount.get(lastIndex).getValue();
                currentIndex = lastIndex + 1;
                return recursiveSearch(currentMoneyAmount, indexes, currentIndex, sumForTake, sum);
            }
        }

        return indexes;
    }

    private List<Notes> getDescSortedListOfAllCash() {
        var currentMoneyAmount = new ArrayList<Notes>();

        cash.keySet().forEach(note -> {
            for (int i = 0; i < cash.get(note).getAmount(); i++) {
                currentMoneyAmount.add(note);
            }
        });
        currentMoneyAmount.sort((o1, o2) -> o2.compare(o1));
        return currentMoneyAmount;
    }

}
