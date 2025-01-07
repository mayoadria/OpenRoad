/**
 * Enumeració que representa els estats possibles d'una incidència.
 * Cada estat està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum EstatIncidencia {

    /**
     * Estat de la incidència oberta.
     */
    OBERTA("Oberta"),

    /**
     * Estat de la incidència tancada.
     */
    TANCADA("Tancada");

    /**
     * Valor descriptiu associat a l'estat de la incidència.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat a l'estat de la incidència.
     */
    EstatIncidencia(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat a l'estat de la incidència.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
