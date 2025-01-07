/**
 * Classe que representa l'històric d'una reserva.
 * Aquesta classe està emmagatzemada en una col·lecció MongoDB anomenada "reserves".
 * Conté informació sobre la data d'inici, la data de finalització, el preu,
 * l'estat, el client i el vehicle associats a la reserva.
 */
package com.copernic.projecte2_openroad.model.mongodb;

// Java
import java.time.LocalDate;

// MongoDB
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatReserva;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un registre d'històric d'una reserva en MongoDB.
 */
@Document(collection = "reserves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricReserva {

    /**
     * Identificador únic de la reserva.
     */
    @Id
    private String idReserva;

    /**
     * Data d'inici de la reserva.
     */
    private LocalDate dataInici;

    /**
     * Data de finalització de la reserva.
     */
    private LocalDate dataFinal;

    /**
     * Preu complet de la reserva.
     */
    private Double preuComplet;

    /**
     * Estat actual de la reserva.
     */
    private EstatReserva estatReserva;

    /**
     * DNI del client associat a la reserva.
     */
    private String dniClient;

    /**
     * Matrícula del vehicle associat a la reserva.
     */
    private String matriculaVehicle;
}
