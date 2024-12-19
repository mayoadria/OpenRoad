package com.copernic.projecte2_openroad.model.enums;


public enum EstatVehicle {
    ACTIU("Actiu"), INACTIU("Inactiu"), RESERVAT("Reservat");

    private final String valor;

    EstatVehicle(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}