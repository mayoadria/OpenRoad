package com.copernic.projecte2_openroad.model.enums;

public enum Marxes {
    CINC(5), SIS(6);

    private final int valor;

    Marxes(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}