/**
 * Interfície que representa el repositori per accedir a la taula "admins" a MySQL.
 * Proporciona funcionalitats per gestionar els administradors emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositori JPA per a l'entitat {@link Admin}.
 */
public interface AdminRepositorySQL extends JpaRepository<Admin, String> {

    /**
     * Cerca un administrador pel nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari de l'administrador.
     * @return l'administrador amb el nom d'usuari especificat, o null si no existeix.
     */
    Admin findByNomUsuari(String nomUsuari);
}
