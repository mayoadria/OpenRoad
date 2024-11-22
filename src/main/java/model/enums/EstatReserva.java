package model.enums;

public enum EstatReserva {
    PENDENT("Pendent"), ACCEPTADA("Acceptada"), FINALITZADA("Finalitzada"), ANULLADA("Anul·lada");

    private final String valor;

    EstatReserva(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
