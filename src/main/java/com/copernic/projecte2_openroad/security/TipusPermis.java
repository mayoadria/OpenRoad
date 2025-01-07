/**
 * Enumeració que representa els tipus de permisos disponibles en el sistema.
 * Defineix els nivells d'accés dels usuaris segons el seu rol.
 */
package com.copernic.projecte2_openroad.security;

public enum TipusPermis {
    /**
     * Permís per als usuaris amb rol d'administrador.
     */
    ADMIN,

    /**
     * Permís per als usuaris amb rol d'agent.
     */
    AGENT,

    /**
     * Permís per als usuaris amb rol de client.
     */
    CLIENT
}
