/**
 * Interfície que representa el repositori per accedir a la taula "clients" a MySQL.
 * Proporciona funcionalitats per gestionar els clients emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositori JPA per a l'entitat {@link Client}.
 */
public interface ClientRepositorySQL extends JpaRepository<Client, String> {

    /**
     * Cerca un client pel seu nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari del client.
     * @return el client amb el nom d'usuari especificat, o null si no existeix.
     */
    Client findByNomUsuari(String nomUsuari);
}
