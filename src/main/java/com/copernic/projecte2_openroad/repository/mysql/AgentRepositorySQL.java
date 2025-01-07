/**
 * Interfície que representa el repositori per accedir a la taula "agents" a MySQL.
 * Proporciona funcionalitats per gestionar els agents emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositori JPA per a l'entitat {@link Agent}.
 */
public interface AgentRepositorySQL extends JpaRepository<Agent, String> {

    /**
     * Cerca una llista d'agents pel seu nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari de l'agent.
     * @return una llista d'agents amb el nom d'usuari especificat.
     */
    List<Agent> findByNomUsuari(String nomUsuari);

    /**
     * Verifica si ja existeix un agent amb el correu electrònic especificat.
     *
     * @param email el correu electrònic de l'agent.
     * @return true si ja existeix un agent amb aquest correu electrònic, false en cas contrari.
     */
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);

}
