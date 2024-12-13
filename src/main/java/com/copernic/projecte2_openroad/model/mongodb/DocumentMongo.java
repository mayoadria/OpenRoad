package com.copernic.projecte2_openroad.model.mongodb;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMongo {

    @Id
    private String dni; // Referència al DNI de l'usuari
    private List<Binary> clientDocs;

}
