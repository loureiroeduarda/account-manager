package com.github.loureiroeduarda.menu;

import com.github.loureiroeduarda.service.Service;

import java.util.Scanner;

public class GeneralPanelMenu {
    public void generalPanelMenu(Scanner sc, Service service) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("===============================================");
            System.out.println("Digite a opção desejada para: ");
            System.out.println("1 - Exibir resumo das contas");
            System.out.println("2 - Exibir resumo de receitas e despesas do mês atual");
            System.out.println("3 - Exibir saldo geral dos últimos seis meses");
            System.out.println("0 - Para retornar ao menu inicial");
            System.out.println("===============================================");
            String chosenOptionText = sc.nextLine();
            int chosenOption = service.convertStringToInt(chosenOptionText);
            switch (chosenOption) {
                case 1:
                    System.out.println("Não implementado");
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
