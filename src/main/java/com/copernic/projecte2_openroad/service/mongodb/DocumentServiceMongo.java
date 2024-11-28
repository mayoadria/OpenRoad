package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.HistoricDocument;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.DocumentRepositoryMongo;

@Service
public class DocumentServiceMongo {

    @Autowired
    private DocumentRepositoryMongo documentRepoMongo;

    // Crear Document.
    public String guardarDocument(HistoricDocument Document) {
        try {
            documentRepoMongo.save(Document);
            String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdDocument() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + Document.getIdDocument() + "). Excepció: " + e.getMessage();
            return msg;     
        }
    }

    // Llistar Document.
    public HistoricDocument llistarDocumentPerId(String id) {
        return documentRepoMongo.findById(id).get();
    }

    // Llistar tots els Documents.
    public List<HistoricDocument> llistarDocuments() {
        return documentRepoMongo.findAll();
    }

    // Modificar Document.
    public String modificarDocument(HistoricDocument Document) {
        try {
            if (llistarDocumentPerId(Document.getNomDocument()) != null) {
                documentRepoMongo.save(Document);
                String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdDocument() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Document: ID(" + Document.getIdDocument() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Document: ID(" + Document.getIdDocument() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Document.
    public String eliminarDocumentPerId(String id) {
        HistoricDocument Document = llistarDocumentPerId(id);
        try {
            if (Document != null) {
                documentRepoMongo.delete(Document);
                String msg = "Document: " + Document.getNomDocument() + " amb ID(" + Document.getIdDocument() + ") esborrat correctament!";
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
