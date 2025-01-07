/**
 * Enumeració que representa els nivells de reputació disponibles.
 * Cada nivell de reputació està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum Reputacio {

    /**
     * Nivell de reputació normal.
     */
    NORMAL("Normal"),

    /**
     * Nivell de reputació premium.
     */
    PREMIUM("Premium");

    /**
     * Valor descriptiu associat al nivell de reputació.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al nivell de reputació.
     */
    Reputacio(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al nivell de reputació.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
