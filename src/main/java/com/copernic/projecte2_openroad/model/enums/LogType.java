package com.copernic.projecte2_openroad.model.enums;

public enum LogType {
    CRITICAL("Critical"), ERROR("Error"), WARNING("Warning"), INFO("Info");

    private final String valor;

    LogType(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
