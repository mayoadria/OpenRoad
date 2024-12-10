package com.copernic.projecte2_openroad.service.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
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

    public void guardarDocuments(DocumentClient documentClient) throws Exception {

        documentRepositoryMongo.save(documentClient);
    }
}
