package com.fincontrol.model;

public enum TransactionType {
    RECEITA("Receita"),
    DESPESA("Despesa");

    private final String descricao;

    TransactionType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}