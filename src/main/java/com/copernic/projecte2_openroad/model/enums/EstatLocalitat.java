package com.copernic.projecte2_openroad.model.enums;


public enum EstatLocalitat {
    ASSIGNADA("Assignada"), LLIURE("Lliure");
    private final String valor;

    EstatLocalitat(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}