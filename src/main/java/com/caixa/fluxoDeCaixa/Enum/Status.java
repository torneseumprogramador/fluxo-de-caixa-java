package com.caixa.fluxoDeCaixa.Enum;

public enum Status {
    DESPESA(0),
    RECEITA(1);

    private final int valor;

    Status(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
