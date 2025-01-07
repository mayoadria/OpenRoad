/**
 * Classe que representa un comentari fet per un client.
 * Emmagatzema informació sobre el client, el vehicle, el contingut del comentari,
 * la data, la recomanació i les valoracions.
 */
package com.copernic.projecte2_openroad.model.mongodb;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un comentari d'un client emmagatzemat en una col·lecció MongoDB anomenada "comentaris".
 */
@Document(collection = "comentaris")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentari {

    /**
     * Identificador únic del comentari.
     */
    @Id
    private String idComentari;

    /**
     * Nom del client que fa el comentari.
     */
    private String nomClient;

    /**
     * Primer cognom del client que fa el comentari.
     */
    private String cognom1Client;

    /**
     * Nom d'usuari del client que fa el comentari.
     */
    private String nomUsuariClient;

    /**
     * Matrícula del vehicle sobre el qual es fa el comentari.
     */
    private String matriculaVehicle;

    /**
     * Títol del comentari.
     */
    private String titolComent;

    /**
     * Missatge complet del comentari.
     */
    private String missatgeComentari;

    /**
     * Data en què es va fer el comentari.
     */
    private LocalDate dataComentari;

    /**
     * Indica si el client recomana el vehicle (true si el recomana, false en cas contrari).
     */
    private Boolean recomanacio;

    /**
     * Valoració donada pel client (p. ex., d'1 a 5 estrelles).
     */
    private int valoracio;

    /**
     * Nombre de "likes" que ha rebut el comentari.
     */
    private int like;

    /**
     * Nombre de "dislikes" que ha rebut el comentari.
     */
    private int disLike;
}
