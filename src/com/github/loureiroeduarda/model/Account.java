package com.github.loureiroeduarda.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int bankCode;
    private final int agencyNumber;
    private final int accountNumber;
    private double accountBalance = 0;

    private List<Transaction> transactionList = new ArrayList<>();

    public Account(int bankCode, int agencyNumber, int accountNumber) {
        this.bankCode = bankCode;
        this.agencyNumber = agencyNumber;
        this.accountNumber = accountNumber;
    }

    public int getBankCode() {
        return bankCode;
    }

    public int getAgencyNumber() {
        return agencyNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        return "Código do banco: " + bankCode + "\n" + "Número da agência:" + agencyNumber + "\n"
                + "Número da conta:" + accountNumber + "\n" + "Saldo da conta: " + accountBalance;
    }
}
