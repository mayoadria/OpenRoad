/**
 * Enumeració que representa una llista de països.
 * Cada país està associat a un valor descriptiu.
 */
package com.copernic.projecte2_openroad.model.enums;

public enum Pais {

    /**
     * País Alemanya.
     */
    ALEMANYA("Alemanya"),

    /**
     * País Argentina.
     */
    ARGENTINA("Argentina"),

    /**
     * País Àustria.
     */
    ÀUSTRIA("Àustria"),

    /**
     * País Bèlgica.
     */
    BÈLGICA("Bèlgica"),

    /**
     * País Brasil.
     */
    BRASIL("Brasil"),

    /**
     * País Canadà.
     */
    CANADÀ("Canadà"),

    /**
     * País Colòmbia.
     */
    COLÒMBIA("Colòmbia"),

    /**
     * País Dinamarca.
     */
    DINAMARCA("Dinamarca"),

    /**
     * País Espanya.
     */
    ESPANYA("Espanya"),

    /**
     * País Estats Units.
     */
    ESTATS_UNITS("Estats Units"),

    /**
     * País França.
     */
    FRANÇA("França"),

    /**
     * País Grècia.
     */
    GRÈCIA("Grècia"),

    /**
     * País Itàlia.
     */
    ITÀLIA("Itàlia"),

    /**
     * País Mèxic.
     */
    MÈXIC("Mèxic"),

    /**
     * País Països Baixos.
     */
    PAÏSOS_BAIXOS("Països Baixos"),

    /**
     * País Perú.
     */
    PERÚ("Perú"),

    /**
     * País Polònia.
     */
    POLÒNIA("Polònia"),

    /**
     * País Portugal.
     */
    PORTUGAL("Portugal"),

    /**
     * País Regne Unit.
     */
    REGNE_UNIT("Regne Unit"),

    /**
     * País Suècia.
     */
    SUÈCIA("Suècia"),

    /**
     * País Suïssa.
     */
    SUÏSSA("Suïssa"),

    /**
     * País Xile.
     */
    XILE("Xile");

    /**
     * Valor descriptiu associat al país.
     */
    private final String valor;

    /**
     * Constructor de l'enumeració.
     *
     * @param valor el valor descriptiu associat al país.
     */
    Pais(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna el valor descriptiu associat al país.
     *
     * @return el valor descriptiu.
     */
    public String getValor() {
        return valor;
    }
}
