package com.fincontrol.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private String id;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private Category category;
    private double amount;

    public Transaction() {
        this.id = java.util.UUID.randomUUID().toString();
        this.date = LocalDate.now();
    }

    public Transaction(LocalDate date, String description, TransactionType type,
                       Category category, double amount) {
        this();
        this.date = date;
        this.description = description;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s: R$ %.2f (%s)",
                date, description, amount, category.getName());
    }
}