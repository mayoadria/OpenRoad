/**
 * Interfície que representa el repositori per accedir a la taula "incidencies" a MySQL.
 * Proporciona funcionalitats per gestionar les incidències emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Incidencia;

/**
 * Repositori JPA per a l'entitat {@link Incidencia}.
 */
@Repository
public interface IncidenciaRepositorySQL extends JpaRepository<Incidencia, Long> {
}
