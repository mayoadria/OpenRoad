package com.copernic.projecte2_openroad.model.mysql;

// Java
import java.time.LocalDate;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;

/**
 * Representa una incidencia dentro del sistema. Esta clase es una entidad JPA que se mapea a la tabla "incidencia" en la base de datos.
 * La entidad {@code Incidencia} almacena información sobre una incidencia, incluyendo su estado, título, coste, fechas y el vehículo asociado.
 *

 */
@Entity
@Table(name = "incidencia")
@Data // Genera automáticamente los métodos getters, setters, toString(), equals() y hashCode()
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
public class Incidencia {

    /**
     * Identificador único de la incidencia en la base de datos.
     * Este campo es la clave primaria de la tabla y se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Long idIncidencia;

    /**
     * Estado de la incidencia, representado mediante un valor del tipo {@code EstatIncidencia}.
     * Este campo no puede ser nulo y se mapea a la columna "estat_incidencia" en la base de datos.
     *
     * @see EstatIncidencia
     */
    @Column(nullable = false, name = "estat_incidencia")
    @Enumerated(EnumType.STRING)
    private EstatIncidencia estatIncidencia;

    /**
     * Título de la incidencia. Este campo es obligatorio y se mapea a la columna "titol" en la base de datos.
     */
    @Column(nullable = false)
    private String titol;

    /**
     * Coste de la incidencia. Este campo es obligatorio y se mapea a la columna "cost" en la base de datos.
     */
    @Column(nullable = false)
    private Double cost;

    /**
     * Fecha de inicio de la incidencia. Este campo es obligatorio y se mapea a la columna "data_inici" en la base de datos.
     */
    @Column(nullable = false, name = "data_inici")
    private LocalDate dataInici;

    /**
     * Fecha de finalización de la incidencia. Este campo es opcional y se mapea a la columna "data_final" en la base de datos.
     */
    @Column(nullable = true, name = "data_final")
    private LocalDate dataFinal;

    /**
     * Vehículo asociado a la incidencia. Este campo establece una relación {@code ManyToOne} con la entidad {@code Vehicle}.
     * El campo "vehicle" en la tabla "incidencia" se mapea con la columna "matricula" de la tabla "vehicle".
     * Este campo no se incluye en el {@code toString()} ni en el {@code equals()} para evitar referencias circulares.
     *
     * @see Vehicle
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", referencedColumnName = "matricula")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}