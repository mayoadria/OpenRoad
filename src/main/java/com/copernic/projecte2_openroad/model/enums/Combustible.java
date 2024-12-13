package com.copernic.projecte2_openroad.model.enums;

public enum Combustible {

    BENZINA95("Benzina 95"), BENZINA98("Benzina 98"), DIESELE("Dièsel e+"), DIESEL10E("Dièsel 10e+"),
    APEU("A peu (Picapedra)"), FUSTA("Fusta"), CARBO("Carbó"), DINOSAURIO("Suc de dinosaure");

    private final String valor;

    Combustible(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
