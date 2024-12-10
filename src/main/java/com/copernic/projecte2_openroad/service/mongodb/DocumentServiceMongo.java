package com.copernic.projecte2_openroad.service.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentMongo;
import com.copernic.projecte2_openroad.repository.mongodb.DocumentRepositoryMongo;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceMongo {

    @Autowired
    private DocumentRepositoryMongo documentRepositoryMongo;

    public void guardarDocuments(String dni, MultipartFile dniFile, MultipartFile carnetFile) throws Exception {
        DocumentMongo document = new DocumentMongo();
        document.setDni(dni);

        // Convertir les imatges a format binari
        document.setDniImatge(new Binary(dniFile.getBytes()));
        document.setDniContentType(dniFile.getContentType());

        document.setCarnetImatge(new Binary(carnetFile.getBytes()));
        document.setCarnetContentType(carnetFile.getContentType());

        // Guardar a MongoDB
        documentRepositoryMongo.save(document);
    }
}
