package com.github.loureiroeduarda.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Validator {

    public static int validatePositiveInt(int number) {
        if (number < 0) {
            return Integer.MAX_VALUE;
        }
        return number;
    }

    public static double validatePositiveDouble(Double number) {
        if (number < 0) {
            return Double.MAX_VALUE;
        }
        return number;
    }

    public static int validateCodeBank(int number, Scanner sc) {
        while (number < 100 || number > 999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String bankCode = sc.nextLine();
            number = ConvertNumber.convertToInt(bankCode);
        }
        return number;
    }

    public static int validateAgencyNumber(int number, Scanner sc) {
        while (number < 1000 || number > 9999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String agencyNumber = sc.nextLine();
            number = ConvertNumber.convertToInt(agencyNumber);
        }
        return number;
    }

    public static int validateAccountNumber(int number, Scanner sc) {
        while (number < 10000 || number > 99999) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String accountNumber = sc.nextLine();
            number = ConvertNumber.convertToInt(accountNumber);
        }
        return number;
    }

    public static double validateTransactionValue(double number, Scanner sc) {
        while (number == Double.MAX_VALUE) {
            System.out.println("Opção inválida! Tente Novamente!!");
            String value = sc.nextLine();
            number = ConvertNumber.convertToDouble(value);
        }
        return number;
    }

    public static LocalDate validateDate(String dateText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate localDate = LocalDate.parse(dateText, formatter);
            return validateFutureDate(localDate);
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println("Data inválida!! Digite novamente!!");
        }
        return null;
    }

    public static LocalDate validateFutureDate(LocalDate localDate) {
        if (localDate.isAfter(LocalDate.now())) {
            System.out.println("O sistema não trabalha com agendamentos!!");
            return null;
        }
        return localDate;
    }

}
