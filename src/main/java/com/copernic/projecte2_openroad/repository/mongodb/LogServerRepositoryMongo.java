/**
 * Interfície que representa el repositori per accedir a la col·lecció "logs_server" a MongoDB.
 * Proporciona funcionalitats per gestionar els registres de log del servidor emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.LogsServer;

/**
 * Repositori MongoDB per a l'entitat {@link LogsServer}.
 */
@Repository
public interface LogServerRepositoryMongo extends MongoRepository<LogsServer, String> {

}
