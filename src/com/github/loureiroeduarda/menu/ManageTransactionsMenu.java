package com.github.loureiroeduarda.menu;

import com.github.loureiroeduarda.service.Service;

import java.util.Scanner;

public class ManageTransactionsMenu {
    public void manageTransactionsMenu(Scanner sc, Service service) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("===============================================");
            System.out.println("Digite a opção desejada para: ");
            System.out.println("1 - Exibir extrato da conta");
            System.out.println("2 - Adicionar transação");
            System.out.println("3 - Editar a última transação");
            System.out.println("4 - Transferir valores");
            System.out.println("0 - Para retornar ao menu inicial");
            System.out.println("===============================================");
            String chosenOptionText = sc.nextLine();
            int chosenOption = service.convertStringToInt(chosenOptionText);
            switch (chosenOption) {
                case 1:
                    System.out.println("Não implementado");
                    break;
                case 2:
                    service.addTransaction(sc);
                    break;
                case 3:
                    System.out.println("Não implementado");
                    break;
                case 4:
                    System.out.println("Não implementado");
                    break;
                case 0:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente Novamente!!");
            }
        }
    }
}
