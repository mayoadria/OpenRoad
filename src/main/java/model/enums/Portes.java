package model.enums;

public enum Portes {
    TRES(3), CINC(5);

    private final int valor;

    Portes(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}