package com.github.loureiroeduarda.model;

import java.time.LocalDate;

public class Transaction {
    LocalDate date;
    String type;
    String category;
    String description;
    Double value;

    public Transaction(LocalDate date, String type, String category, String description, Double value) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.description = description;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " Data: " + date +
                " | Tipo: " + type +
                " | Categoria: " + category +
                " | Descrição: " + description +
                " | Valor: " + value;
    }
}
