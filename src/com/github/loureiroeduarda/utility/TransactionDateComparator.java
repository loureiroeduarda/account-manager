package com.github.loureiroeduarda.utility;

import com.github.loureiroeduarda.model.Transaction;

import java.util.Comparator;

public class TransactionDateComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
