/**
 * Enumeració que representa els estats possibles d'una reserva.
 * Cada estat està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum EstatReserva {

    /**
     * Estat de la reserva pendent.
     */
    PENDENT("Pendent"),

    /**
     * Estat de la reserva acceptada.
     */
    ACCEPTADA("Acceptada"),

    /**
     * Estat de la reserva finalitzada.
     */
    FINALITZADA("Finalitzada"),

    /**
     * Estat de la reserva anul·lada.
     */
    ANULLADA("Anul·lada");

    /**
     * Valor descriptiu associat a l'estat de la reserva.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat a l'estat de la reserva.
     */
    EstatReserva(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat a l'estat de la reserva.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
