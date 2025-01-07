/**
 * Enumeració que representa els estats possibles d'una localitat.
 * Cada estat està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum EstatLocalitat {

    /**
     * Estat de la localitat assignada.
     */
    ASSIGNADA("Assignada"),

    /**
     * Estat de la localitat lliure.
     */
    LLIURE("Lliure");

    /**
     * Valor descriptiu associat a l'estat de la localitat.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat a l'estat de la localitat.
     */
    EstatLocalitat(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat a l'estat de la localitat.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
