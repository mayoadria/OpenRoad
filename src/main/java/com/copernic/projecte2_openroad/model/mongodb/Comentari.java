package com.copernic.projecte2_openroad.model.mongodb;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "comentaris")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentari {

    @Id
    private String idComentari;

    private String nomClient;
    private String cognom1Client;
    private String nomUsuariClient;
    private String matriculaVehicle; 


    private String titolComent;
    private String missatgeComentari;
    private LocalDate dataComentari;
    private Boolean recomanacio;
    private int valoracio;
    
    private int like;
    private int disLike;

}
