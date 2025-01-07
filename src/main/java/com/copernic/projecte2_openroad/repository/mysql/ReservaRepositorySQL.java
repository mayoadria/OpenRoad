/**
 * Interfície que representa el repositori per accedir a la taula "reserves" a MySQL.
 * Proporciona funcionalitats per gestionar les reserves emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositori JPA per a l'entitat {@link Reserva}.
 */
@Repository
public interface ReservaRepositorySQL extends JpaRepository<Reserva, Long> {

    /**
     * Cerca una reserva associada a un vehicle específic.
     *
     * @param vehicle l'entitat del vehicle associat.
     * @return una reserva opcional associada al vehicle especificat.
     */
    Optional<Reserva> findByVehicle(Vehicle vehicle);

    /**
     * Cerca una llista de reserves associades a un client pel seu nom d'usuari.
     *
     * @param usuario el nom d'usuari del client.
     * @return una llista de reserves associades al client especificat.
     */
    List<Reserva> findByClient_nom(String usuario);

    /**
     * Cerca una llista de reserves associades a un client pel seu DNI.
     *
     * @param dni el DNI del client.
     * @return una llista de reserves associades al client especificat.
     */
    List<Reserva> findByClient_dni(String dni);
}
