/**
 * Interfície que representa el repositori per accedir a la taula "imagenes" a MySQL.
 * Proporciona funcionalitats per gestionar les imatges emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositori JPA per a l'entitat {@link Imagen}.
 */
public interface ImagenesRepositorySQL extends JpaRepository<Imagen, Long> {

}
