/**
 * Classe que representa un document associat a un client.
 * Aquesta classe està emmagatzemada en una col·lecció MongoDB anomenada "documents_clients".
 * Conté informació sobre l'identificador del document, el client associat i els documents.
 */
package com.copernic.projecte2_openroad.model.mongodb;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Representa un document d'un client emmagatzemat a MongoDB.
 */
@Document(collection = "documents_clients")
public class DocumentClient {

    /**
     * Identificador únic del document en MongoDB.
     */
    @Id
    private String id;

    /**
     * Identificador del client associat al document (relació amb MySQL).
     */
    private String idClient;

    /**
     * Mapa que conté noms de documents com a claus i els seus continguts binaris com a valors.
     */
    private Map<String, Binary> clientDoc;

    /**
     * Retorna l'identificador del document.
     *
     * @return l'identificador del document.
     */
    public String getId() {
        return id;
    }

    /**
     * Assigna l'identificador del document.
     *
     * @param id l'identificador del document.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna l'identificador del client associat al document.
     *
     * @return l'identificador del client.
     */
    public String getIdClient() {
        return idClient;
    }

    /**
     * Assigna l'identificador del client associat al document.
     *
     * @param idClient l'identificador del client.
     */
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    /**
     * Retorna el mapa de documents del client.
     *
     * @return el mapa de documents del client.
     */
    public Map<String, Binary> getClientDoc() {
        return clientDoc;
    }

    /**
     * Assigna el mapa de documents del client.
     *
     * @param clientDoc el mapa de documents del client.
     */
    public void setClientDoc(Map<String, Binary> clientDoc) {
        this.clientDoc = clientDoc;
    }
}
