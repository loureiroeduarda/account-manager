package com.github.loureiroeduarda.model;

public class Account {
    int bankCode;
    int agencyNumber;
    int accountNumber;
    double accountBalance;


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
}
