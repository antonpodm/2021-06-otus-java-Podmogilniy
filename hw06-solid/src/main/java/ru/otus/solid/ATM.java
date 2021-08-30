package ru.otus.solid;

import ru.otus.solid.interfaces.IATM;
import ru.otus.solid.interfaces.INote;
import ru.otus.solid.interfaces.IWallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ATM implements IATM {

    private final Map<INote, IWallet> cash;

    public ATM(Map<INote, IWallet> startCash) {
        this.cash = startCash;
    }

    public void putCash(INote note, int sum) throws IOException {
        var wallet = cash.get(note);
        if (wallet != null) {
            wallet.put(sum);
        } else {
            throw new IOException("Банкомат не поддерживает купюры такого типа");
        }
    }

    public int getCashBalance() {
        var balance = 0;
        for (INote note : cash.keySet()) {
            IWallet wallet = cash.get(note);
            var sum = wallet.getBalance() * note.getValue();
            balance += sum;
        }

        return balance;
    }

  /*  public void takeCash(int amount) {
        var notes = new ArrayList<INote>();
        notes.addAll(cash.keySet());
        notes.sort(new Comparator<INote>() {
            @Override
            public int compare(INote o1, INote o2) {
                //reverse order
                return Integer.compare(o2.getValue(), o1.getValue());
            }
        });

    }*/

  /*  private boolean checkPossibility(List<INote> notes, int amount) {
        for (INote note : notes) {
            if(amount % note.getValue() == 0){
                if(cash.get(note).getBalance() > amount){
                    return true;
                }
            } else {
                if(amount > note.getValue()){

                }
            }
        }

        return false;
    }*/

}
