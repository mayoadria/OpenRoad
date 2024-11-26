package com.copernic.projecte2_openroad.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricDocument {

    @Id
    private String idDocument;

    private String nomDocument;
    private String tipusDocument;
    private String documentLink;
}
