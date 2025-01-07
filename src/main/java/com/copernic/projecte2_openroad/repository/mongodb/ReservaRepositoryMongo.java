/**
 * Interfície que representa el repositori per accedir a la col·lecció "reserves" a MongoDB.
 * Proporciona funcionalitats per gestionar l'històric de reserves emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.HistoricReserva;

/**
 * Repositori MongoDB per a l'entitat {@link HistoricReserva}.
 */
@Repository
public interface ReservaRepositoryMongo extends MongoRepository<HistoricReserva, String> {

}
