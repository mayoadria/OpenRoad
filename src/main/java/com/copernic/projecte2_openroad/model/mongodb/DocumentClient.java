package com.copernic.projecte2_openroad.model.mongodb;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "documents_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentClient {

    @Id
    private String idClient;
    private List<Binary> clientDoc;
}
