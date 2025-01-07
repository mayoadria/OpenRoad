/**
 * Servei que gestiona les operacions relacionades amb els documents dels clients
 * emmagatzemats en una col·lecció MongoDB.
 */
package com.copernic.projecte2_openroad.service.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import com.copernic.projecte2_openroad.repository.mongodb.DocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe de servei per a la gestió dels documents dels clients a MongoDB.
 */
@Service
public class DocumentServiceMongo {

    @Autowired
    private DocumentRepositoryMongo documentRepositoryMongo;

    /**
     * Guarda un document associat a un client a MongoDB.
     *
     * @param document l'entitat del document a guardar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String guardarDocument(DocumentClient document) {
        try {
            documentRepositoryMongo.save(document);
            String msg = "Document: " + document.getClass() + " amb ID(" + document.getIdClient() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + document.getIdClient() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
