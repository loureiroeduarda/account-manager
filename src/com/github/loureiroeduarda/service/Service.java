package com.github.loureiroeduarda.service;

import com.github.loureiroeduarda.model.Account;
import com.github.loureiroeduarda.model.Transaction;
import com.github.loureiroeduarda.repository.RepositoryAccount;
import com.github.loureiroeduarda.utility.ConvertNumber;
import com.github.loureiroeduarda.utility.TransactionDateComparator;
import com.github.loureiroeduarda.utility.Validator;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Service {

    private final RepositoryAccount repositoryAccount;

    public Service() {
        this.repositoryAccount = new RepositoryAccount();
    }

    public void loadData() {
        repositoryAccount.loadAccounts();
    }

    public void registerAccount(Scanner sc) {
        System.out.println("Informe o código do banco [100 à 999]: ");
        String bankCodeText = sc.nextLine();
        int bankCode = ConvertNumber.convertToInt(bankCodeText);
        bankCode = Validator.validateCodeBank(bankCode, sc);

        System.out.println("Informe o número da agência [1000 à 9999]: ");
        String agencyNumberText = sc.nextLine();
        int agencyNumber = ConvertNumber.convertToInt(agencyNumberText);
        agencyNumber = Validator.validateAgencyNumber(agencyNumber, sc);

        System.out.println("Informe o número da conta [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = ConvertNumber.convertToInt(accountNumberText);
        accountNumber = Validator.validateAccountNumber(accountNumber, sc);

        repositoryAccount.saveAccount(bankCode, agencyNumber, accountNumber);
        System.out.println("Conta cadastrada com sucesso!!");
    }

    public void removeAccount(Scanner sc) {
        System.out.println("Informe o número da conta que deseja remover [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = ConvertNumber.convertToInt(accountNumberText);
        accountNumber = Validator.validateAccountNumber(accountNumber, sc);
        Account AccountNumberFind = findAccount(sc, accountNumber);

        System.out.println("Todos os dados da conta " + "(" + AccountNumberFind + ")" + " serão excluídos. ");
        System.out.println("Para prosseguir com a remoção digite 'R' e para cancelar a remoção digite 'C': ");

        boolean keepGoing = true;
        while (keepGoing) {
            String chosenOption = sc.nextLine();
            if (chosenOption.equalsIgnoreCase("R")) {
                repositoryAccount.deleteAccount(accountNumber);
                System.out.println("Conta removida com sucesso!!");
                keepGoing = false;
            } else if (chosenOption.equalsIgnoreCase("C")) {
                System.out.println("A conta não foi removida!!");
                keepGoing = false;
            } else {
                System.out.println("Opção inválida! Por favor, responda com 'R' ou 'C' !!");
            }
        }
    }

    public void mergeAccounts(Scanner sc) {
        System.out.println("Para mesclar contas digite o número da conta de origem [10000 à 99999]: ");
        String sourceAccountText = sc.nextLine();
        int sourceAccount = ConvertNumber.convertToInt(sourceAccountText);
        sourceAccount = Validator.validateAccountNumber(sourceAccount, sc);
        Account sourceAccountFind = findAccount(sc, sourceAccount);

        System.out.println("Para mesclar contas digite o número da conta de destino [10000 à 99999]: ");
        String destinationAccountText = sc.nextLine();
        int destinationAccount = ConvertNumber.convertToInt(destinationAccountText);
        destinationAccount = Validator.validateAccountNumber(destinationAccount, sc);
        Account destinationAccountFind = findAccount(sc, destinationAccount);

        destinationAccountFind.getTransactionList().addAll(sourceAccountFind.getTransactionList());
        sourceAccountFind.getTransactionList().clear();

        destinationAccountFind.getTransactionList().sort(new TransactionDateComparator());
        System.out.println("Contas mescladas com sucesso!!");
        System.out.println("Todas as transações da conta nº. : " + destinationAccountFind.getAccountNumber());
        printTransactions(destinationAccountFind.getTransactionList());
    }

    private void printTransactions(List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void displayAccountStatement(Scanner sc) {
        System.out.println("Para exibir o extrato digite o número da conta desejada [10000 à 99999]: ");
        String accountText = sc.nextLine();
        int account = ConvertNumber.convertToInt(accountText);
        account = Validator.validateAccountNumber(account, sc);
        Account accountFind = findAccount(sc, account);

        if (accountFind.getTransactionList().size() != 0) {
            accountFind.getTransactionList().sort(new TransactionDateComparator());

            double partialBalance = 0;
            for (Transaction transaction : accountFind.getTransactionList()) {
                if (transaction.getType().equalsIgnoreCase("R")) {
                    partialBalance += transaction.getValue();
                } else {
                    partialBalance -= transaction.getValue();
                }
                System.out.println(transaction);
                System.out.println(" Saldo: R$ " + partialBalance);
            }
        } else {
            System.out.println(" Esta conta não possui transações!!");
        }
        System.out.println(" Saldo total: R$ " + accountFind.getAccountBalance());
    }

    public void addTransaction(Scanner sc) {
        System.out.println("Para incluir uma nova transação digite o número da conta desejada [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = ConvertNumber.convertToInt(accountNumberText);
        accountNumber = Validator.validateAccountNumber(accountNumber, sc);
        Account AccountNumberFind = findAccount(sc, accountNumber);

        if (AccountNumberFind != null) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("Insira o tipo da transação. Para receita digite 'R', para despesa digite 'D' e para retornar ao menu de gerenciamento de transações digite 'S': ");
                String type = sc.nextLine();

                if (type.equalsIgnoreCase("R")) {
                    Transaction transaction = createIncomeTransaction(sc, type);
                    AccountNumberFind.getTransactionList().add(transaction);

                    System.out.println(" Seu saldo era de R$ " + AccountNumberFind.getAccountBalance());
                    double currentBalance = AccountNumberFind.getAccountBalance() + transaction.getValue();
                    AccountNumberFind.setAccountBalance(currentBalance);
                    System.out.println(" Transação adicionada com sucesso!! (" + transaction + ")");
                    System.out.println(" Seu saldo atual é de R$ " + currentBalance);

                } else if (type.equalsIgnoreCase("D")) {
                    Transaction transaction = createExpenseTransaction(sc, type);
                    AccountNumberFind.getTransactionList().add(transaction);

                    System.out.println(" Seu saldo era de R$ " + AccountNumberFind.getAccountBalance());
                    double currentBalance = AccountNumberFind.getAccountBalance() - transaction.getValue();
                    AccountNumberFind.setAccountBalance(currentBalance);
                    System.out.println(" Transação adicionada com sucesso!! (" + transaction + ")");
                    System.out.println(" Seu saldo atual é de R$ " + currentBalance);

                } else if (type.equalsIgnoreCase("S")) {
                    keepGoing = false;

                } else {
                    System.out.println("Opção inválida! Por favor, responda com 'R', 'D' OU 'S' !!");
                }
            }
        }
    }

    public void editLastTransaction(Scanner sc) {
        System.out.println("Para editar a última transação adcionada digite o número da conta correspondente [10000 à 99999]: ");
        String accountNumberText = sc.nextLine();
        int accountNumber = ConvertNumber.convertToInt(accountNumberText);
        accountNumber = Validator.validateAccountNumber(accountNumber, sc);
        Account account = findAccount(sc, accountNumber);

        if (account != null) {
            boolean keepGoing = true;
            while (keepGoing) {
                int transactionListSize = account.getTransactionList().size();
                Transaction transaction = account.getTransactionList().get(transactionListSize - 1);
                System.out.println("A edição será realizada na seguinte transação (" + transaction + ")");
                System.out.println("Para editar esta transação digite 'E' e para retornar ao menu de gerenciamento de transações digite 'S': ");
                String chosenOption = sc.nextLine();

                if (chosenOption.equalsIgnoreCase("E")) {
                    System.out.println("Informe o tipo da transação que deseja cadastradar. Para receita digite 'R' e para despesa digite 'D': ");
                    String type = sc.nextLine();
                    while (!type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("D")) {
                        System.out.println("Opção inválida! Por favor, responda com 'R' ou 'D' !!");
                        type = sc.nextLine();
                    }

                    if (type.equalsIgnoreCase("R")) {
                        double accountBalance = updateAccountBalance(account, transaction);
                        transaction = createIncomeTransaction(sc, type);
                        account.getTransactionList().set(transactionListSize - 1, transaction);
                        accountBalance += transaction.getValue();
                        account.setAccountBalance(accountBalance);

                        System.out.println("A transação foi editada com sucesso!!");
                        keepGoing = false;

                    } else if (type.equalsIgnoreCase("D")) {
                        double accountBalance = updateAccountBalance(account, transaction);
                        transaction = createExpenseTransaction(sc, type);
                        account.getTransactionList().set(transactionListSize - 1, transaction);
                        accountBalance -= transaction.getValue();
                        account.setAccountBalance(accountBalance);

                        System.out.println("A transação foi editada com sucesso!!");
                        keepGoing = false;

                    } else {
                        System.out.println("Opção inválida! Por favor, responda com 'R' ou 'D' !!");
                    }

                } else if (chosenOption.equalsIgnoreCase("S")) {
                    keepGoing = false;

                } else {
                    System.out.println("Opção inválida! Por favor, responda com 'E' ou 'S' !!");
                }
            }
        }
    }

    public void transferFunds(Scanner sc) {
        System.out.println("Para transferir valores digite o número da conta de origem [10000 à 99999]: ");
        String sourceAccountText = sc.nextLine();
        int sourceAccount = ConvertNumber.convertToInt(sourceAccountText);
        sourceAccount = Validator.validateAccountNumber(sourceAccount, sc);
        Account sourceAccountFind = findAccount(sc, sourceAccount);

        System.out.println("Para transferir valores digite o número da conta de destino [10000 à 99999]: ");
        String destinationAccountText = sc.nextLine();
        int destinationAccount = ConvertNumber.convertToInt(destinationAccountText);
        destinationAccount = Validator.validateAccountNumber(destinationAccount, sc);
        Account destinationAccountFind = findAccount(sc, destinationAccount);

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Informe o valor que deseja transferir para a conta informada: ");
            String amountTransferredText = sc.nextLine();
            double amountTransferred = ConvertNumber.convertToDouble(amountTransferredText);
            amountTransferred = Validator.validateTransactionValue(amountTransferred, sc);

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
                System.out.println("O saldo atual da conta nº. " + sourceAccountFind.getAccountNumber() + " é R$ " + sourceAccountFind.getAccountBalance());
                System.out.println("O saldo atual da conta nº. " + destinationAccountFind.getAccountNumber() + " é R$ " + destinationAccountFind.getAccountBalance());
                keepGoing = false;
            } else {
                System.out.println("O saldo é insuficiente para concluir a transação! Digite novamente!!");
            }
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
                        incomeText = incomeText.concat(transaction.toString()).concat("\n");
                    } else {
                        expense += transaction.getValue();
                        expenseText = expenseText.concat(transaction.toString()).concat("\n");
                    }
                }
            }
            System.out.println(" Resumo das receitas do mês atual da conta nº. " + account.getAccountNumber() + ": ");
            System.out.println(" Total de receitas: R$ " + income);
            System.out.println(incomeText);
            System.out.println(" Resumo das despesas do mês atual da conta nº. " + account.getAccountNumber() + ": ");
            System.out.println(" Total de despesas: R$ " + expense);
            System.out.println(expenseText);
        }
    }

    public void balanceGenerateLastSixMonths() {
        List<Account> allAccounts = repositoryAccount.listAll();
        LocalDate currentDateMinusSixMonths = LocalDate.now().minusMonths(6);

        for (Account account : allAccounts) {
            Map<Month, List<Transaction>> mapTransactionsByMonth = new HashMap<>();

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

    private Transaction createIncomeTransaction(Scanner sc, String type) {
        LocalDate localDate = readDate(sc);
        System.out.println("Insira a categoria da receita [Ex: Salário]: ");
        String income = sc.nextLine();
        System.out.println("Insira a descrição da receita: ");
        String description = sc.nextLine();
        System.out.println("Insira o valor da receita: ");
        String valueText = sc.nextLine();
        double value = ConvertNumber.convertToDouble(valueText);
        value = Validator.validateTransactionValue(value, sc);

        return new Transaction(localDate, type, income, description, value);
    }

    private Transaction createExpenseTransaction(Scanner sc, String type) {
        LocalDate localDate = readDate(sc);
        System.out.println("Insira a categoria da despesa [Ex: Alimentação, Casa, Luz]: ");
        String expense = sc.nextLine();
        System.out.println("Insira a descrição da despesa: ");
        String description = sc.nextLine();
        System.out.println("Insira o valor da despesa: ");
        String valueText = sc.nextLine();
        double value = ConvertNumber.convertToDouble(valueText);
        value = Validator.validateTransactionValue(value, sc);

        return new Transaction(localDate, type, expense, description, value);
    }

    private LocalDate readDate(Scanner sc) {
        System.out.println("Insira a data da transação [dd/mm/aaaa]: ");
        String dateText = sc.nextLine();
        LocalDate localDate = Validator.validateDate(dateText);

        while (localDate == null) {
            dateText = sc.nextLine();
            localDate = Validator.validateDate(dateText);
        }
        return localDate;
    }

    private double updateAccountBalance(Account account, Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("R")) {
            return account.getAccountBalance() - transaction.getValue();
        }
        return account.getAccountBalance() + transaction.getValue();
    }

    private Account findAccount(Scanner sc, int accountNumber) {
        Account account = repositoryAccount.getAccount(accountNumber);
        while (account == null) {
            System.out.println("Está conta não existe! Digite uma conta válida!!");
            String accountText = sc.nextLine();
            int newAccountNumber = Validator.validateAccountNumber(ConvertNumber.convertToInt(accountText), sc);
            account = repositoryAccount.getAccount(newAccountNumber);
        }
        return account;
    }

}

