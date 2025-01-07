/**
 * Interfície que representa el repositori per accedir a la col·lecció "documents_clients" a MongoDB.
 * Proporciona mètodes per gestionar els documents dels clients emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repositori MongoDB per a l'entitat {@link DocumentClient}.
 */
public interface DocumentRepositoryMongo extends MongoRepository<DocumentClient, String> {

    /**
     * Cerca un document associat a un client mitjançant l'identificador del client.
     *
     * @param idClient l'identificador del client.
     * @return el document associat al client amb l'identificador especificat.
     */
    DocumentClient findByIdClient(String idClient);
}
