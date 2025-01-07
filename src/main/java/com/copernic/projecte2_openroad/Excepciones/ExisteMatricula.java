package com.copernic.projecte2_openroad.Excepciones;

public class ExisteMatricula extends RuntimeException{
    public ExisteMatricula(String message) {
        super (message);
    }
}
