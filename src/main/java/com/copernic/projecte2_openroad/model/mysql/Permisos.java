package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.security.TipusPermis;
import org.springframework.security.core.GrantedAuthority;


/**
 * Representa un permiso asociado a un tipo de permiso específico. Esta clase implementa la interfaz {@link GrantedAuthority}
 * de Spring Security, lo que permite utilizar esta clase como una autoridad de seguridad en el sistema.
 * La clase almacena un tipo de permiso, que se define mediante la enumeración {@link TipusPermis}.
 *
 * @see org.springframework.security.core.GrantedAuthority
 * @see TipusPermis
 */
public class Permisos implements GrantedAuthority {

    /**
     * El tipo de permiso asociado a este objeto.
     * Este campo determina el permiso que se le asigna a un usuario o entidad.
     */
    private TipusPermis permis;

    /**
     * Constructor de la clase {@code Permisos}.
     *
     * @param permis El tipo de permiso que se asignará a esta instancia de {@code Permisos}.
     */
    public Permisos(TipusPermis permis) {
        this.permis = permis;
    }

    /**
     * Devuelve la autoridad representada por este objeto de permiso.
     * Este método es parte de la implementación de la interfaz {@link GrantedAuthority} y devuelve el tipo de permiso
     * como un {@code String}.
     *
     * @return La autoridad asociada a este permiso, representada como una cadena (nombre del tipo de permiso).
     */
    @Override
    public String getAuthority() {
        return permis.toString();
    }
}
