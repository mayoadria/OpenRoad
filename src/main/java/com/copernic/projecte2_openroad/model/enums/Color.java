package com.copernic.projecte2_openroad.model.enums;

public enum Color {

    VERMELL("Vermell"),
    NEGRE("Negre"),
    BLANC("Blanc"),
    GROC("Groc"),
    BLAU("Blau"),
    VERD("Verd"),
    GRIS("Gris"),
    PLATEJAT("Platejat"),
    BEIX("Beix"),
    TARONJA("Taronja"),
    LILA("Lila"),
    ROSA("Rosa"),
    MARRO("Marro"),
    TURQUESA("Turquesa"),
    MARI("Mari"),
    DAURAT("Daurat"),
    PORPRA("Porpra"),
    BLANCPERLAT("Blanc Perlat"),
    NEGREMATE("Negre Mate"),
    BLAUCLAR("Blau Clar"),
    BLAUNOTANTFOSC("Blau No Tant Fosc"),
    /*MARROPERUA("Marro Perua")*/;


    private final String valor;

    Color(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
