package com.copernic.projecte2_openroad.model.enums;

public enum Pais {
    ALEMANYA("Alemanya"),
    ARGENTINA("Argentina"),
    ÀUSTRIA("Àustria"),
    BÈLGICA("Bèlgica"),
    BRASIL("Brasil"),
    CANADÀ("Canadà"),
    COLÒMBIA("Colòmbia"),
    DINAMARCA("Dinamarca"),
    ESPANYA("Espanya"),
    ESTATS_UNITS("Estats Units"),
    FRANÇA("França"),
    GRÈCIA("Grècia"),
    ITÀLIA("Itàlia"),
    MÈXIC("Mèxic"),
    PAÏSOS_BAIXOS("Països Baixos"),
    PERÚ("Perú"),
    POLÒNIA("Polònia"),
    PORTUGAL("Portugal"),
    REGNE_UNIT("Regne Unit"),
    SUÈCIA("Suècia"),
    SUÏSSA("Suïssa"),
    XILE("Xile");

    private final String valor;

    Pais(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
