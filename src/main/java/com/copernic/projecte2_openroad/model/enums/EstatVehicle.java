/**
 * Enumeració que representa els estats possibles d'un vehicle.
 * Cada estat està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum EstatVehicle {

    /**
     * Estat del vehicle actiu.
     */
    ACTIU("Actiu"),

    /**
     * Estat del vehicle inactiu.
     */
    INACTIU("Inactiu"),

    /**
     * Estat del vehicle reservat.
     */
    RESERVAT("Reservat"),

    /**
     * Estat del vehicle lliurat.
     */
    ENTREGAT("Lliurat");

    /**
     * Valor descriptiu associat a l'estat del vehicle.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat a l'estat del vehicle.
     */
    EstatVehicle(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat a l'estat del vehicle.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
