package com.github.loureiroeduarda.service;

import com.github.loureiroeduarda.repository.RepositoryAccount;

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

        repositoryAccount.saveAccountList(bankCode, agencyNumber, accountNumber);
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
}
