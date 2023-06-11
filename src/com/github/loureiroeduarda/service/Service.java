package com.github.loureiroeduarda.service;

import com.github.loureiroeduarda.model.Account;
import com.github.loureiroeduarda.model.Transaction;
import com.github.loureiroeduarda.repository.RepositoryAccount;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Service {

    private final RepositoryAccount repositoryAccount;

    public Service() {
        this.repositoryAccount = new RepositoryAccount();
    }

    public int convertStringToInt(String text) {
        int optionInt = Integer.MAX_VALUE;
        try {
            optionInt = Integer.parseInt(text);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Você não digitou um número!!");
        }
        optionInt = validatePositiveNumber(optionInt);
        return optionInt;
    }

    private int validatePositiveNumber(int number) {
        if (number < 0) {
            return Integer.MAX_VALUE;
        }
        return number;
    }

    public void registerAccount(Scanner sc) {
        System.out.println("Informe o código do banco [100 à 999]: ");
        String bankCodeText = sc.nextLine();
        int bankCode = convertStringToInt(bankCodeText);
        bankCode = validateCodeBank(bankCode, sc);

        System.out.println("Informe o número da agência [1000 à 9999]: ");
        String agencyNumberText = sc.nextLine();
        int agencyNumber = convertStringToInt(agencyNumberText);
        agencyNumber = validateAgencyNumber(agencyNumber, sc);

        System.out.println("Informe o número da conta [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = convertStringToInt(accountNumberText);
        accountNumber = validateAccountNumber(accountNumber, sc);

        repositoryAccount.saveAccount(bankCode, agencyNumber, accountNumber);
        System.out.println("Conta cadastrada com sucesso!!");
        System.out.println(repositoryAccount.listAll());
    }

    private int validateCodeBank(int number, Scanner sc) {
        while (number < 100 || number > 999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String bankCode = sc.nextLine();
            number = convertStringToInt(bankCode);
        }
        return number;
    }

    private int validateAgencyNumber(int number, Scanner sc) {
        while (number < 1000 || number > 9999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String agencyNumber = sc.nextLine();
            number = convertStringToInt(agencyNumber);
        }
        return number;
    }

    private int validateAccountNumber(int number, Scanner sc) {
        while (number < 10000 || number > 99999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String accountNumber = sc.nextLine();
            number = convertStringToInt(accountNumber);
        }
        return number;
    }

    public void removeAccount(Scanner sc) {
        System.out.println("Informe o número da conta que deseja remover [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = convertStringToInt(accountNumberText);
        accountNumber = validateAccountNumber(accountNumber, sc);

        Account accountFind = repositoryAccount.getAccount(accountNumber);
        System.out.println("Todos os dados da conta " + accountFind + " serão excluídos. Para prosseguir com a remoção digite 'R' e para cancelar a remoção digite 'C': ");

        boolean keepGoing = true;
        while (keepGoing) {
            String chosenOption = sc.nextLine();
            if (chosenOption.equalsIgnoreCase("R")) {
                repositoryAccount.deleteAccount(accountNumber);
                System.out.println("Conta removida com sucesso!!");
                keepGoing = false;
            } else if (chosenOption.equalsIgnoreCase("C")) {
                System.out.println("A conta não será removida!!");
                keepGoing = false;
            } else {
                System.out.println("Opção inválida!! Por favor, responda com 'R' ou 'C' !!");
            }
        }
    }

    public void addTransaction(Scanner sc) {
        System.out.println("Para incluir uma nova transação digite o número da conta desejada [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = convertStringToInt(accountNumberText);
        accountNumber = validateAccountNumber(accountNumber, sc);

        Account accountFind = repositoryAccount.getAccount(accountNumber);

        if (accountFind != null) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("Insira o tipo da transação. Para receita digite 'R', para despesa digite 'D' e para retornar ao menu de gerenciamento de transações digite 'S': ");
                String chosenOption = sc.nextLine();

                if (chosenOption.equalsIgnoreCase("R")) {
                    System.out.println("Insira a data da transação [dd/mm/aaaa]: ");
                    String dateText = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(dateText, formatter);
                    System.out.println("Insira a categoria da receita [Ex: Salário]: ");
                    String revenue = sc.nextLine();
                    System.out.println("Insira a descrição da receita: ");
                    String description = sc.nextLine();
                    System.out.println("Insira o valor da receita: ");
                    String valueText = sc.nextLine();
                    double value = convertStringToDouble(valueText);
                    value = validateTransactionValue(value, sc);

                    Transaction transaction = new Transaction(localDate, chosenOption, revenue, description, value);
                    accountFind.getTransactionList().add(transaction);

                    double currentBalance = accountFind.getAccountBalance() + value;
                    accountFind.setAccountBalance(currentBalance);
                    System.out.println("Seu saldo atual é de R$ " + currentBalance);
                    System.out.println(repositoryAccount.listAll());

                } else if (chosenOption.equalsIgnoreCase("D")) {
                    System.out.println("Insira a data da transação [dd/mm/aaaa]: ");
                    String dateText = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(dateText, formatter);
                    System.out.println("Insira a categoria da despesa [Ex: Alimentação, Casa, Luz]: ");
                    String revenue = sc.nextLine();
                    System.out.println("Insira a descrição da despesa: ");
                    String description = sc.nextLine();
                    System.out.println("Insira o valor da despesa: ");
                    String valueText = sc.nextLine();
                    double value = convertStringToDouble(valueText);
                    value = validateTransactionValue(value, sc);

                    Transaction transaction = new Transaction(localDate, chosenOption, revenue, description, value);
                    accountFind.getTransactionList().add(transaction);

                    double currentBalance = accountFind.getAccountBalance() - value;
                    accountFind.setAccountBalance(currentBalance);
                    System.out.println("Seu saldo atual é de R$ " + currentBalance);
                    System.out.println(repositoryAccount.listAll());

                } else if (chosenOption.equalsIgnoreCase("S")) {
                    keepGoing = false;

                } else {
                    System.out.println("Opção inválida!! Por favor, responda com 'R' ou 'D' !!");
                }
            }
        }
    }

    private double validateTransactionValue(double number, Scanner sc) {
        while (number == Double.MAX_VALUE) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String value = sc.nextLine();
            number = convertStringToDouble(value);
        }
        return number;
    }

    public double convertStringToDouble(String valueText) {
        double optionDouble = Double.MAX_VALUE;
        try {
            optionDouble = Double.parseDouble(valueText);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Você não digitou um número!!");
        }
        optionDouble = validatePositiveNumber(optionDouble);
        return optionDouble;
    }

    private double validatePositiveNumber(Double number) {
        if (number < 0) {
            return Double.MAX_VALUE;
        }
        return number;
    }
}
