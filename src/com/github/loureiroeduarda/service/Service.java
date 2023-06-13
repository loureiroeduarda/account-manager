package com.github.loureiroeduarda.service;

import com.github.loureiroeduarda.model.Account;
import com.github.loureiroeduarda.model.Transaction;
import com.github.loureiroeduarda.model.TransactionDateComparator;
import com.github.loureiroeduarda.repository.RepositoryAccount;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        Account AccountNumberFind = validateAccount(sc, accountNumber);

        System.out.println("Todos os dados da conta " + AccountNumberFind + " serão excluídos. Para prosseguir com a remoção digite 'R' e para cancelar a remoção digite 'C': ");

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
        Account AccountNumberFind = validateAccount(sc, accountNumber);

        if (AccountNumberFind != null) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("Insira o tipo da transação. Para receita digite 'R', para despesa digite 'D' e para retornar ao menu de gerenciamento de transações digite 'S': ");
                String type = sc.nextLine();

                if (type.equalsIgnoreCase("R")) {
                    Transaction transaction = createIncomeTransaction(sc, type);
                    AccountNumberFind.getTransactionList().add(transaction);

                    double currentBalance = AccountNumberFind.getAccountBalance() + transaction.getValue();
                    AccountNumberFind.setAccountBalance(currentBalance);
                    System.out.println("Seu saldo atual é de R$ " + currentBalance);
                    System.out.println(repositoryAccount.listAll());

                } else if (type.equalsIgnoreCase("D")) {
                    Transaction transaction = createExpenseTransaction(sc, type);
                    AccountNumberFind.getTransactionList().add(transaction);

                    double currentBalance = AccountNumberFind.getAccountBalance() - transaction.getValue();
                    AccountNumberFind.setAccountBalance(currentBalance);
                    System.out.println("Seu saldo atual é de R$ " + currentBalance);
                    System.out.println(repositoryAccount.listAll());

                } else if (type.equalsIgnoreCase("S")) {
                    keepGoing = false;

                } else {
                    System.out.println("Opção inválida!! Por favor, responda com 'R', 'D' OU 'S' !!");
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

    private Transaction createIncomeTransaction(Scanner sc, String type) {
        System.out.println("Insira a data da transação [dd/mm/aaaa]: ");
        String dateText = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateText, formatter);
        System.out.println("Insira a categoria da receita [Ex: Salário]: ");
        String income = sc.nextLine();
        System.out.println("Insira a descrição da receita: ");
        String description = sc.nextLine();
        System.out.println("Insira o valor da receita: ");
        String valueText = sc.nextLine();
        double value = convertStringToDouble(valueText);
        value = validateTransactionValue(value, sc);

        return new Transaction(localDate, type, income, description, value);
    }

    private Transaction createExpenseTransaction(Scanner sc, String type) {
        System.out.println("Insira a data da transação [dd/mm/aaaa]: ");
        String dateText = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateText, formatter);
        System.out.println("Insira a categoria da despesa [Ex: Alimentação, Casa, Luz]: ");
        String expense = sc.nextLine();
        System.out.println("Insira a descrição da despesa: ");
        String description = sc.nextLine();
        System.out.println("Insira o valor da despesa: ");
        String valueText = sc.nextLine();
        double value = convertStringToDouble(valueText);
        value = validateTransactionValue(value, sc);

        return new Transaction(localDate, type, expense, description, value);
    }

    public void editLastTransaction(Scanner sc) {
        System.out.println("Para editar a última transação adcionada digite o número da conta correspondente [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = convertStringToInt(accountNumberText);
        accountNumber = validateAccountNumber(accountNumber, sc);
        Account AccountNumberFind = validateAccount(sc, accountNumber);

        if (AccountNumberFind != null) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("Para editar a última transação digite 'E' e para retornar ao menu de gerenciamento de transações digite 'S': ");
                String chosenOption = sc.nextLine();
                if (chosenOption.equalsIgnoreCase("E")) {
                    int transactionListSize = AccountNumberFind.getTransactionList().size();
                    Transaction transaction = AccountNumberFind.getTransactionList().get(transactionListSize - 1);

                    System.out.println("Informe o tipo da transação que deseja cadastradar. Para receita digite 'R' e para despesa digite 'D': ");
                    String type = sc.nextLine();
                    while (!type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("D")) {
                        System.out.println("Opção inválida!! Por favor, responda com 'R' ou 'D' !!");
                        type = sc.nextLine();
                    }
                    if (type.equalsIgnoreCase("R")) {
                        double accountBalance = updateAccountBalance(AccountNumberFind, transaction);
                        transaction = createIncomeTransaction(sc, type);
                        AccountNumberFind.getTransactionList().add(transactionListSize - 1, transaction);
                        accountBalance += transaction.getValue();
                        AccountNumberFind.setAccountBalance(accountBalance);

                        System.out.println("A última transação foi editada com sucesso!!");
                        keepGoing = false;

                    } else if (type.equalsIgnoreCase("D")) {
                        double accountBalance = updateAccountBalance(AccountNumberFind, transaction);
                        transaction = createExpenseTransaction(sc, type);
                        AccountNumberFind.getTransactionList().add(transactionListSize - 1, transaction);
                        accountBalance += transaction.getValue();
                        AccountNumberFind.setAccountBalance(accountBalance);

                        System.out.println("A última transação foi editada com sucesso!!");
                        keepGoing = false;

                    } else {
                        System.out.println("Opção inválida!! Por favor, responda com 'R' ou 'D' !!");
                    }

                } else if (chosenOption.equalsIgnoreCase("S")) {
                    keepGoing = false;

                } else {
                    System.out.println("Opção inválida!! Por favor, responda com 'E' ou 'S' !!");
                }
            }
        }
    }

    public double updateAccountBalance(Account account, Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("R")) {
            return account.getAccountBalance() - transaction.getValue();
        }
        return account.getAccountBalance() + transaction.getValue();
    }

    public void mergeAccounts(Scanner sc) {
        System.out.println("Para mesclar contas digite o número da conta de origem [10000 à 99999]: ");
        String sourceAccountText = sc.nextLine();
        int sourceAccount = convertStringToInt(sourceAccountText);
        sourceAccount = validateAccountNumber(sourceAccount, sc);
        Account sourceAccountFind = validateAccount(sc, sourceAccount);

        System.out.println("Para mesclar contas digite o número da conta de destino [10000 à 99999]: ");
        String destinationAccountText = sc.nextLine();
        int destinationAccount = convertStringToInt(destinationAccountText);
        destinationAccount = validateAccountNumber(destinationAccount, sc);
        Account destinationAccountFind = validateAccount(sc, destinationAccount);

        destinationAccountFind.getTransactionList().addAll(sourceAccountFind.getTransactionList());
        sourceAccountFind.getTransactionList().clear();

        destinationAccountFind.getTransactionList().sort(new TransactionDateComparator());
        System.out.println(destinationAccountFind.getTransactionList());
    }

    public void transferFunds(Scanner sc) {
        System.out.println("Para transferir valores digite o número da conta de origem [10000 à 99999]: ");
        String sourceAccountText = sc.nextLine();
        int sourceAccount = convertStringToInt(sourceAccountText);
        sourceAccount = validateAccountNumber(sourceAccount, sc);
        Account sourceAccountFind = validateAccount(sc, sourceAccount);

        System.out.println("Para transferir valores digite o número da conta de destino [10000 à 99999]: ");
        String destinationAccountText = sc.nextLine();
        int destinationAccount = convertStringToInt(destinationAccountText);
        destinationAccount = validateAccountNumber(destinationAccount, sc);
        Account destinationAccountFind = validateAccount(sc, destinationAccount);

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Informe o valor que deseja transferir para a conta informada: ");
            String amountTransferredText = sc.nextLine();
            double amountTransferred = convertStringToDouble(amountTransferredText);
            amountTransferred = validateTransactionValue(amountTransferred, sc);

            if (amountTransferred <= sourceAccountFind.getAccountBalance()) {
                double sourceAccountCurrentBalance = sourceAccountFind.getAccountBalance() - amountTransferred;
                sourceAccountFind.setAccountBalance(sourceAccountCurrentBalance);
                double targetAccountCurrentBalance = destinationAccountFind.getAccountBalance() + amountTransferred;
                destinationAccountFind.setAccountBalance(targetAccountCurrentBalance);

                Transaction sourceTransaction = new Transaction(LocalDate.now(), "D", "Saque", "Saque", amountTransferred);
                sourceAccountFind.getTransactionList().add(sourceTransaction);

                Transaction destinationTransaction = new Transaction(LocalDate.now(), "R", "Depósito", "Depósito", amountTransferred);
                destinationAccountFind.getTransactionList().add(destinationTransaction);

                System.out.println("A transferência de valores foi realizada com sucesso!!");
                System.out.println(repositoryAccount.listAll());
                keepGoing = false;
            } else {
                System.out.println("O saldo é insuficiente para concluir a transação!!");
            }
        }
    }

    private Account validateAccount(Scanner sc, int accountNumber) {
        Account account = repositoryAccount.getAccount(accountNumber);
        while (account == null) {
            System.out.println("Está conta não existe!! Digite uma conta válida!!");
            String accountText = sc.nextLine();
            int newAccountNumber = convertStringToInt(accountText);
            newAccountNumber = validateAccountNumber(accountNumber, sc);
            account = repositoryAccount.getAccount(accountNumber);
        }
        return account;
    }

    public void loadData() {
        repositoryAccount.loadAccounts();
    }

    public void displayAccountStatement(Scanner sc) {
        System.out.println("Para exibir o extrato digite o número da conta desejada [10000 à 99999]: ");
        String accountText = sc.nextLine();
        int account = convertStringToInt(accountText);
        account = validateAccountNumber(account, sc);
        Account accountFind = validateAccount(sc, account);

        accountFind.getTransactionList().sort(new TransactionDateComparator());

        double partialBalance = 0;
        for (Transaction transaction : accountFind.getTransactionList()) {
            if (transaction.getType().equalsIgnoreCase("R")) {
                partialBalance += transaction.getValue();
            } else {
                partialBalance -= transaction.getValue();
            }
            System.out.println(transaction);
            System.out.println(partialBalance);
        }
    }

    public void summaryOfAccounts() {
        List<Account> accountList = repositoryAccount.listAll();

        double balance = 0;
        for (Account account : accountList) {
            balance += account.getAccountBalance();
            System.out.println(account);
        }
        System.out.println("O saldo total considerando todas as contas cadastrada é de R$: " + balance);
    }

    public void summaryOfIncomeExpensesForMonth() {
        List<Account> accountList = repositoryAccount.listAll();

        for (Account account : accountList) {
            double income = 0;
            String incomeText = "";
            double expense = 0;
            String expenseText = "";
            for (Transaction transaction : account.getTransactionList()) {
                if (LocalDate.now().getMonth() == transaction.getDate().getMonth()) {
                    if (transaction.getType().equalsIgnoreCase("R")) {
                        income += transaction.getValue();
                        incomeText = incomeText.concat(transaction.toString()).concat(" ");
                    } else {
                        expense += transaction.getValue();
                        expenseText = expenseText.concat(transaction.toString()).concat(" ");
                    }
                }
            }
            System.out.println("Resumo das receitas do mês atual da conta nº. " + account.getAccountNumber() + ": " + income + incomeText);
            System.out.println("Resumo das despesas do mês atual da conta nº. " + account.getAccountNumber() + ": " + expense + expenseText);
        }
    }

    public void balanceGenerateLastSixMonths() {
        List<Account> allAccounts = repositoryAccount.listAll();
        LocalDate currentDateMinusSixMonths = LocalDate.now().minusMonths(6);

        Map<Month, List<Transaction>> mapTransactionsByMonth = new HashMap<>();

        for (Account account : allAccounts) {
            for (Transaction transaction : account.getTransactionList()) {
                if (transaction.getDate().isAfter(currentDateMinusSixMonths)) {
                    mapTransactionsByMonth.putIfAbsent(transaction.getDate().getMonth(), new ArrayList<>());
                    mapTransactionsByMonth.get(transaction.getDate().getMonth()).add(transaction);
                }
            }

            for (Map.Entry<Month, List<Transaction>> entry : mapTransactionsByMonth.entrySet()) {
                List<Transaction> transactionsByMonth = entry.getValue();
                double monthbalance = 0.0;
                for (Transaction transaction : transactionsByMonth) {
                    if (transaction.getType().equalsIgnoreCase("R")) {
                        monthbalance += transaction.getValue();
                    } else {
                        monthbalance -= transaction.getValue();
                    }
                }
                System.out.println("Conta nº. " + account.getAccountNumber() + ": O saldo do mês de " + entry.getKey() + " é R$ " + monthbalance);
            }
        }
    }
}

