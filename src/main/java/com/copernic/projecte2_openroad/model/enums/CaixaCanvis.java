/**
 * Enumeració que representa els tipus de caixa de canvis disponibles.
 * Proporciona dues opcions: Manual i Automàtica.
 * Cada opció està associada a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum CaixaCanvis {
    /**
     * Tipus de caixa de canvis manual.
     */
    MANUAL("Manual"),

    /**
     * Tipus de caixa de canvis automàtica.
     */
    AUTOMATIC("Automàtic");

    /**
     * Valor descriptiu associat al tipus de caixa de canvis.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al tipus de caixa de canvis.
     */
    CaixaCanvis(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al tipus de caixa de canvis.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}