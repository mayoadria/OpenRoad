package com.copernic.projecte2_openroad.model.enums;


public enum CaixaCanvis {
    MANUAL("Manual"), AUTOMATIC("Automàtic");

    private final String valor;

    CaixaCanvis(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}