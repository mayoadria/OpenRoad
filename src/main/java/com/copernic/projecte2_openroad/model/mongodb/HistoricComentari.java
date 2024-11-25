package com.copernic.projecte2_openroad.model.mongodb;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.copernic.projecte2_openroad.model.mysql.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricComentari {

    @Id
    private Long idComentari;

    private Client client;
    private LocalDate dataComentari;
    private Float valoracio;
    private Boolean recomanacio;
    private String missatgeComentari;
    private int like;
}
