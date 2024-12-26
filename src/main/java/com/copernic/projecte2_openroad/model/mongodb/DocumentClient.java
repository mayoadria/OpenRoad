package com.copernic.projecte2_openroad.model.mongodb;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "documents_clients")
public class DocumentClient {

    @Id
    private String id; // Identificador de MongoDB

    private String idClient; // Identificador del client (relació amb MySQL)

    private Map<String, Binary> clientDoc; // Mapa amb noms (claus) i documents (valors)

    // Getters i Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Map<String, Binary> getClientDoc() {
        return clientDoc;
    }

    public void setClientDoc(Map<String, Binary> clientDoc) {
        this.clientDoc = clientDoc;
    }
}
