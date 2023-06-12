package com.github.loureiroeduarda.repository;

import com.github.loureiroeduarda.model.Account;
import com.github.loureiroeduarda.model.Transaction;

import java.time.LocalDate;
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

    public void loadAccounts() {
        saveAccount(101, 6030, 75603);
        createTransaction(LocalDate.of(2023, 1, 5), "R", "Salário", "12/2022", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 1, 10), "D", "Casa", "Aluguel", 1700.0, 75603);
        createTransaction(LocalDate.of(2023, 2, 5), "R", "Salário", "01/2023", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 2, 10), "D", "Casa", "Aluguel", 1700.0, 75603);
        createTransaction(LocalDate.of(2023, 3, 5), "R", "Salário", "02/2023", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 3, 10), "D", "Casa", "Aluguel", 1700.0, 75603);
        createTransaction(LocalDate.of(2023, 4, 5), "R", "Salário", "03/2023", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 4, 10), "D", "Casa", "Aluguel", 1700.0, 75603);
        createTransaction(LocalDate.of(2023, 5, 5), "R", "Salário", "04/2023", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 5, 10), "D", "Casa", "Aluguel", 1700.0, 75603);
        createTransaction(LocalDate.of(2023, 6, 5), "R", "Salário", "05/2023", 5000.0, 75603);
        createTransaction(LocalDate.of(2023, 6, 10), "D", "Casa", "Aluguel", 1700.0, 75603);

        saveAccount(222, 563, 90523);
        createTransaction(LocalDate.of(2023, 1, 10), "R", "Salário", "12/2022", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 1, 25), "D", "Alimentação", "Restaurante Bolinha", 250.0, 90523);
        createTransaction(LocalDate.of(2023, 2, 10), "R", "Salário", "01/2023", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 2, 25), "D", "Lazer", "Pub House", 300.0, 90523);
        createTransaction(LocalDate.of(2023, 3, 10), "R", "Salário", "02/2023", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 3, 25), "D", "Alimentação", "Supermercado", 700.0, 90523);
        createTransaction(LocalDate.of(2023, 4, 10), "R", "Salário", "03/2023", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 4, 25), "D", "Alimentação", "Supermercado", 850.0, 90523);
        createTransaction(LocalDate.of(2023, 5, 10), "R", "Salário", "04/2023", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 5, 25), "D", "Lazer", "Boliche", 150.0, 90523);
        createTransaction(LocalDate.of(2023, 6, 10), "R", "Salário", "05/2023", 2500.0, 90523);
        createTransaction(LocalDate.of(2023, 6, 25), "D", "Alimentação", "Restaurante Bolinha", 300.0, 90523);

        saveAccount(704, 234, 40023);
        createTransaction(LocalDate.of(2023, 1, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 1, 19), "D", "Casa", "Prestação financiamento", 2500.0, 40023);
        createTransaction(LocalDate.of(2023, 2, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 2, 19), "D", "Casa", "Prestação financiamento", 2500.0, 40023);
        createTransaction(LocalDate.of(2023, 3, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 3, 19), "D", "Casa", "Prestação financiamento", 2500.0, 40023);
        createTransaction(LocalDate.of(2023, 4, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 4, 19), "D", "Alimentação", "Restaurante Comida Boa", 450.0, 40023);
        createTransaction(LocalDate.of(2023, 5, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 5, 19), "D", "Lazer", "Karokê", 250.0, 40023);
        createTransaction(LocalDate.of(2023, 6, 7), "R", "Salário", "12/2022", 7000.0, 40023);
        createTransaction(LocalDate.of(2023, 6, 19), "D", "Alimentação", "Restaurante Juquinha", 300.0, 40023);
    }

    public void saveAccount(int bankCode, int agencyNumber, int accountNumber) {
        Account account = new Account(bankCode, agencyNumber, accountNumber);
        this.accountList.add(account);
    }

    private void createTransaction(LocalDate date, String type, String category, String description, Double value, int accountNumber) {
        Transaction transaction = new Transaction(date, type, category, description, value);
        Account account = getAccount(accountNumber);
        account.getTransactionList().add(transaction);
        updateAccountBalance(account, transaction);
    }

    public void updateAccountBalance(Account account, Transaction transaction) {
        double currentBalance;
        if (transaction.getType().equalsIgnoreCase("R")) {
            currentBalance = account.getAccountBalance() + transaction.getValue();
        } else {
            currentBalance = account.getAccountBalance() - transaction.getValue();
        }
        account.setAccountBalance(currentBalance);
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
