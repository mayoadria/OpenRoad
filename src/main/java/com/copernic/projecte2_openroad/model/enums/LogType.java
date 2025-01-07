/**
 * Enumeració que representa els tipus de registres (logs) disponibles.
 * Cada tipus de registre està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum LogType {

    /**
     * Registre de tipus crític.
     */
    CRITICAL("Critical"),

    /**
     * Registre de tipus error.
     */
    ERROR("Error"),

    /**
     * Registre de tipus advertència (warning).
     */
    WARNING("Warning"),

    /**
     * Registre de tipus informatiu.
     */
    INFO("Info");

    /**
     * Valor descriptiu associat al tipus de registre.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al tipus de registre.
     */
    LogType(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al tipus de registre.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
