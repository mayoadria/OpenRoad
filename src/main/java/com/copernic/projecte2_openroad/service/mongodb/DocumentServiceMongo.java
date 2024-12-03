package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.DocumentRepositoryMongo;

@Service
public class DocumentServiceMongo {

    @Autowired
    private DocumentRepositoryMongo documentRepoMongo;

    // Crear Document.
    public String guardarDocument(DocumentClient Document) {
        try {
            documentRepoMongo.save(Document);
            String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdClient() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + Document.getIdClient() + "). Excepció: " + e.getMessage();
            return msg;     
        }
    }

    // Llistar Document.
    public DocumentClient llistarDocumentPerId(String id) {
        return documentRepoMongo.findById(id).get();
    }

    // Llistar tots els Documents.
    public List<DocumentClient> llistarDocuments() {
        return documentRepoMongo.findAll();
    }

    // Modificar Document.
    public String modificarDocument(DocumentClient Document) {
        try {
            if (llistarDocumentPerId(Document.getNomDocument()) != null) {
                documentRepoMongo.save(Document);
                String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdClient() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Document: ID(" + Document.getIdClient() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + Document.getIdClient() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Document.
    public String eliminarDocumentPerId(String id) {
        DocumentClient Document = llistarDocumentPerId(id);
        try {
            if (Document != null) {
                documentRepoMongo.delete(Document);
                String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdClient() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Document: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
