package com.copernic.projecte2_openroad.service.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import com.copernic.projecte2_openroad.repository.mongodb.DocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceMongo {

    @Autowired
    private DocumentRepositoryMongo documentRepositoryMongo;

    // Crear Document.
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

