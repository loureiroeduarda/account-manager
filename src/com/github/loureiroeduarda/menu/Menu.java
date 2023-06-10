package com.github.loureiroeduarda.menu;

import com.github.loureiroeduarda.service.Service;

import java.util.Scanner;

public class Menu {

    private final Scanner sc;
    private final Service service;
    private final ManageAccountsMenu manageAccountsMenu;
    private final ManageTransactionsMenu manageTransactionsMenu;
    private final GeneralPanelMenu generalPanelMenu;

    public Menu() {
        this.sc = new Scanner(System.in);
        this.service = new Service();
        this.manageAccountsMenu = new ManageAccountsMenu();
        this.manageTransactionsMenu = new ManageTransactionsMenu();
        this.generalPanelMenu = new GeneralPanelMenu();
    }

    public void menu() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("===============================================");
            System.out.println("Bem vindo ao sistema Gerenciador de Contas");
            System.out.println("Digite a opção desejada para: ");
            System.out.println("1 - Gerenciar contas");
            System.out.println("2 - Gerenciar transações");
            System.out.println("3 - Painel geral");
            System.out.println("0 - Encerrar o sistema");
            System.out.println("===============================================");
            String chosenOptionText = sc.nextLine();
            int chosenOption = service.convertStringToInt(chosenOptionText);
            switch (chosenOption) {
                case 1:
                    manageAccountsMenu.manageAccountsMenu(sc, service);
                    break;
                case 2:
                    manageTransactionsMenu.manageTransactionsMenu(sc, service);
                    break;
                case 3:
                    generalPanelMenu.generalPanelMenu(sc, service);
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
