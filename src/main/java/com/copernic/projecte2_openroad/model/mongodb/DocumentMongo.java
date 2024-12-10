package com.copernic.projecte2_openroad.model.mongodb;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMongo {

    @Id
    private String id;

    private String dni; // Referència al DNI de l'usuari
    private Binary dniImatge; // Imatge del DNI
    private Binary carnetImatge; // Imatge del carnet de conduir
    private String dniContentType; // Tipus MIME de la imatge del DNI
    private String carnetContentType; // Tipus MIME de la imatge del carnet

}
