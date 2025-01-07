/**
 * Interfície que representa el repositori per accedir a la taula "localitats" a MySQL.
 * Proporciona funcionalitats per gestionar les localitats emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Localitat;

/**
 * Repositori JPA per a l'entitat {@link Localitat}.
 */
@Repository
public interface LocalitatRepositorySQL extends JpaRepository<Localitat, String> {

    /**
     * Cerca una localitat pel seu codi postal.
     *
     * @param codiPostalLoc el codi postal de la localitat.
     * @return la localitat amb el codi postal especificat, o null si no existeix.
     */
    Localitat findBycodiPostalLoc(String codiPostalLoc);
}
