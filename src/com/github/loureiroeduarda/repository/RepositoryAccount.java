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

    public void saveAccount(int bankCode, int agencyNumber, int accountNumber) {
        Account account = new Account(bankCode, agencyNumber, accountNumber);
        this.accountList.add(account);
    }

    public void deleteAccount(int accountNumber) {
        Account account = getAccount(accountNumber);
        accountList.remove(account);
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Contas cadastradas: " + accountList;
    }
}
