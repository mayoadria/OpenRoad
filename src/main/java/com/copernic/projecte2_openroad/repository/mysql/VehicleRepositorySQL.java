/**
 * Interfície que representa el repositori per accedir a la taula "vehicles" a MySQL.
 * Proporciona funcionalitats per gestionar els vehicles emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.model.enums.EstatVehicle;

import java.util.List;

/**
 * Repositori JPA per a l'entitat {@link Vehicle}.
 */
@Repository
public interface VehicleRepositorySQL extends JpaRepository<Vehicle, String> {

    /**
     * Cerca una llista de vehicles associats a una localitat específica pel seu codi postal.
     *
     * @param codiPostalLoc el codi postal de la localitat.
     * @return una llista de vehicles associats a la localitat especificada.
     */
    List<Vehicle> findByLocalitat_CodiPostalLoc(String codiPostalLoc);

    /**
     * Cerca una llista de vehicles pel seu estat específic.
     *
     * @param estatVehicle l'estat del vehicle (p. ex., ACTIU, INACTIU, RESERVAT).
     * @return una llista de vehicles amb l'estat especificat.
     */
    List<Vehicle> findByEstatVehicle(EstatVehicle estatVehicle);
}
