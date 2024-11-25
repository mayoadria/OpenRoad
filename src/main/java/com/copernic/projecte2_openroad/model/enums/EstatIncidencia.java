package com.copernic.projecte2_openroad.model.enums;

public enum EstatIncidencia {
    OBERTA("Oberta"), TANCADA("Tancada");

    private final String valor;

    EstatIncidencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}