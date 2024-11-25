package com.copernic.projecte2_openroad.model.enums;


public enum Reputacio {
    NORMAL("Normal"), PREMIUM("Premium");

    private final String valor;

    Reputacio(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}