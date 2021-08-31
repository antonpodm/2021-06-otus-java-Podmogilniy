package ru.otus.solid.atm;

import ru.otus.solid.exceptions.PutCashException;
import ru.otus.solid.exceptions.TakeCashException;
import ru.otus.solid.interfaces.IATM;
import ru.otus.solid.interfaces.INote;
import ru.otus.solid.interfaces.IWallet;

import java.util.Map;

import static ru.otus.solid.atm.ATMHelper.isNotNull;

public class ATM implements IATM {

    private final Map<INote, IWallet> cash;
    private Integer cashBalance;

    public ATM(Map<INote, IWallet> cash) {
        this.cash = cash;
        cashBalance = getCashBalance();
    }

    public void putCash(int sumForPut) throws PutCashException {
        INote note = ATMHelper.findNote(cash, sumForPut);
        if (isNotNull(note)) {
            IWallet wallet = cash.get(note);
            if (isNotNull(wallet)) {
                wallet.put(sumForPut);
                cashBalance += sumForPut;
                return;
            }
        }
        throw new PutCashException();
    }

    public Integer getCashBalance() {
        if (cashBalance == null) {
            var balance = 0;
            for (INote note : cash.keySet()) {
                IWallet wallet = cash.get(note);
                var sum = wallet.getAmount() * note.getValue();
                balance += sum;
            }
            return balance;
        }
        return cashBalance;
    }
    //Сделал выдачу упрощенной (по одной купюре) т.к. "Оптимизировать выдачу не надо."
    public void takeCash(int sumForTake) throws TakeCashException {
        if (checkPossibility(sumForTake)) {
            INote note = ATMHelper.findNote(cash, sumForTake);
            if (isNotNull(note)) {
                IWallet wallet = cash.get(note);
                if (isNotNull(wallet)) {
                    wallet.take(sumForTake);
                    cashBalance -= sumForTake;
                    return;
                }
            }
        }
        throw new TakeCashException();
    }

    private boolean checkPossibility(int sumForTake) {
        if (cashBalance < sumForTake) {
            return false;
        }
        INote note = ATMHelper.findNote(cash, sumForTake);
        if (isNotNull(note)) {
            IWallet wallet = cash.get(note);
            if (isNotNull(wallet) && wallet.getAmount() > 0) {
                return true;
            }
        }
        return false;
    }

}
