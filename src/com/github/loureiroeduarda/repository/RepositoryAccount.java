package com.github.loureiroeduarda.repository;

import com.github.loureiroeduarda.model.Account;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAccount {
    private final List<Account> accountList;

    public RepositoryAccount() {
        this.accountList = new ArrayList<>();
    }

    public List<Account> listAll() {
        return this.accountList;
    }

    public void saveAccountList(int bankCode, int agencyNumber, int accountNumber) {
        Account account = new Account(bankCode, agencyNumber, accountNumber);
        this.accountList.add(account);
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
