/**
 * Classe que representa l'històric d'una incidència.
 * Aquesta classe està emmagatzemada en una col·lecció MongoDB anomenada "incidencies".
 * Conté informació detallada sobre l'estat, el títol, el motiu, el cost, les dates
 * i els documents associats a la incidència.
 */
package com.copernic.projecte2_openroad.model.mongodb;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un registre d'històric d'una incidència associada a un vehicle.
 */
@Document(collection = "incidencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricIncidencia {

    /**
     * Identificador únic de la incidència.
     */
    @Id
    private String idIncidencia;

    /**
     * Estat actual de la incidència.
     */
    private EstatIncidencia estatIncidencia;

    /**
     * Títol descriptiu de la incidència.
     */
    private String titolInc;

    /**
     * Motiu de la incidència.
     */
    private String motiu;

    /**
     * Cost associat a la resolució de la incidència.
     */
    private Double cost;

    /**
     * Data d'inici de la incidència.
     */
    private LocalDate dataInici;

    /**
     * Data de finalització de la incidència.
     */
    private LocalDate dataFinal;

    /**
     * Matrícula del vehicle associat a la incidència.
     */
    private String matriculaVehicle;

    /**
     * Documents associats a la incidència en forma de llista de bytes.
     */
    private List<Byte> incidenciaDoc;
}
