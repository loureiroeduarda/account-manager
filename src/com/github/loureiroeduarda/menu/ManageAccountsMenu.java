package com.github.loureiroeduarda.menu;

import com.github.loureiroeduarda.service.Service;

import java.util.Scanner;

public class ManageAccountsMenu {

    public void manageAccountsMenu(Scanner sc, Service service) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("===============================================");
            System.out.println("Digite a opção desejada para: ");
            System.out.println("1 - Cadastrar conta");
            System.out.println("2 - Remover conta");
            System.out.println("3 - Mesclar contas");
            System.out.println("0 - Para retornar ao menu inicial");
            System.out.println("===============================================");
            String chosenOptionText = sc.nextLine();
            int chosenOption = service.convertStringToInt(chosenOptionText);
            switch (chosenOption) {
                case 1:
                    service.registerAccount(sc);
                    break;
                case 2:
                    System.out.println("Não implementado");
                    break;
                case 3:
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