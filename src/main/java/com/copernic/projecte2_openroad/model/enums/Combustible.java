/**
 * Enumeració que representa els tipus de combustibles disponibles.
 * Cada tipus de combustible està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum Combustible {

    /**
     * Combustible Benzina 95.
     */
    BENZINA95("Benzina 95"),

    /**
     * Combustible Benzina 98.
     */
    BENZINA98("Benzina 98"),

    /**
     * Combustible Dièsel e+.
     */
    DIESELE("Dièsel e+"),

    /**
     * Combustible Dièsel 10e+.
     */
    DIESEL10E("Dièsel 10e+"),

    /**
     * Opció a peu (Picapedra).
     */
    APEU("A peu (Picapedra)"),

    /**
     * Combustible de fusta.
     */
    FUSTA("Fusta"),

    /**
     * Combustible de carbó.
     */
    CARBO("Carbó"),

    /**
     * Suc de dinosaure com a combustible.
     */
    DINOSAURIO("Suc de dinosaure");

    /**
     * Valor descriptiu associat al tipus de combustible.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al tipus de combustible.
     */
    Combustible(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al tipus de combustible.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
