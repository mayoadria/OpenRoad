package model.enums;

public enum Places {
    DOS(2), TRES(3), QUATRE(4), CINC(5), SET(7);

    private final int valor;

    Places(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}