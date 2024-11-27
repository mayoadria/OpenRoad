package com.copernic.projecte2_openroad.model.enums;

public enum Rol {
    AGENT("Agent"), ADMINISTRADOR("Administrador");

    private final String valor;

    Rol(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}