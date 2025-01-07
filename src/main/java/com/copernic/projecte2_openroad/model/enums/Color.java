/**
 * Enumeració que representa una varietat de colors.
 * Cada color està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum Color {

    /**
     * Color vermell.
     */
    VERMELL("Vermell"),

    /**
     * Color negre.
     */
    NEGRE("Negre"),

    /**
     * Color blanc.
     */
    BLANC("Blanc"),

    /**
     * Color groc.
     */
    GROC("Groc"),

    /**
     * Color blau.
     */
    BLAU("Blau"),

    /**
     * Color verd.
     */
    VERD("Verd"),

    /**
     * Color gris.
     */
    GRIS("Gris"),

    /**
     * Color platejat.
     */
    PLATEJAT("Platejat"),

    /**
     * Color beix.
     */
    BEIX("Beix"),

    /**
     * Color taronja.
     */
    TARONJA("Taronja"),

    /**
     * Color lila.
     */
    LILA("Lila"),

    /**
     * Color rosa.
     */
    ROSA("Rosa"),

    /**
     * Color marró.
     */
    MARRO("Marro"),

    /**
     * Color turquesa.
     */
    TURQUESA("Turquesa"),

    /**
     * Color mari.
     */
    MARI("Mari"),

    /**
     * Color daurat.
     */
    DAURAT("Daurat"),

    /**
     * Color porpra.
     */
    PORPRA("Porpra"),

    /**
     * Color blanc perlat.
     */
    BLANCPERLAT("Blanc Perlat"),

    /**
     * Color negre mate.
     */
    NEGREMATE("Negre Mate"),

    /**
     * Color blau clar.
     */
    BLAUCLAR("Blau Clar"),

    /**
     * Color blau no tant fosc.
     */
    BLAUNOTANTFOSC("Blau No Tant Fosc");

    // Color desactivat (comentat).
    // MARROPERUA("Marro Perua");

    /**
     * Valor descriptiu associat al color.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al color.
     */
    Color(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al color.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
