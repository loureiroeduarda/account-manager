package com.github.loureiroeduarda.utility;

public class ConvertNumber {

    public static int convertToInt(String text) {
        int optionInt = Integer.MAX_VALUE;
        try {
            optionInt = Integer.parseInt(text);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Você não digitou um número!!");
        }
        optionInt = Validator.validatePositiveInt(optionInt);
        return optionInt;
    }

    public static double convertToDouble(String valueText) {
        double optionDouble = Double.MAX_VALUE;
        try {
            optionDouble = Double.parseDouble(valueText);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Você não digitou um número!!");
        }
        optionDouble = Validator.validatePositiveDouble(optionDouble);
        return optionDouble;
    }


}
