/**
 * Interfície que representa el repositori per accedir a la col·lecció "incidencies" a MongoDB.
 * Proporciona funcionalitats per gestionar l'històric d'incidències emmagatzemades.
 */
package com.copernic.projecte2_openroad.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.HistoricIncidencia;

/**
 * Repositori MongoDB per a l'entitat {@link HistoricIncidencia}.
 */
@Repository
public interface IncidenciaRepositoryMongo extends MongoRepository<HistoricIncidencia, String> {
}
