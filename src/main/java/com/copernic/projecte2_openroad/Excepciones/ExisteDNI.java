package com.copernic.projecte2_openroad.Excepciones;

public class ExisteDNI extends RuntimeException {
    public ExisteDNI(String mensaje) {
        super(mensaje);
    }
}
