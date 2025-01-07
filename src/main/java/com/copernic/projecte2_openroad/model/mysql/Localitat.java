package com.copernic.projecte2_openroad.model.mysql;


// Java
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.copernic.projecte2_openroad.model.enums.EstatLocalitat;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa una localización dentro del sistema. Esta clase es una entidad JPA que se mapea a la tabla "localitat" en la base de datos.
 * La entidad {@code Localitat} almacena información sobre una localización, incluyendo su código postal, estado, población,
 * dirección, horarios de apertura y cierre, y las relaciones con los vehículos y el agente asignado a dicha localización.
 *
 * @see javax.persistence.Entity
 * @see javax.persistence.Table
 * @see javax.persistence.Id
 * @see javax.persistence.GeneratedValue
 * @see javax.persistence.Column
 * @see javax.persistence.EnumType
 * @see javax.persistence.Enumerated
 */
@Entity
@Table(name = "localitat")
@Data // Genera automáticamente los métodos getters, setters, toString(), equals() y hashCode()
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
public class Localitat {

    /**
     * Código postal de la localización. Este campo es la clave primaria de la tabla y no puede ser nulo.
     */
    @Id
    @Column(nullable = false, name = "codi_postal_loc")
    private String codiPostalLoc;

    /**
     * Estado de la localización, representado mediante un valor del tipo {@code EstatLocalitat}.
     * Este campo no puede ser nulo y se mapea a la columna "estat_localitat" en la base de datos.
     *
     * @see EstatLocalitat
     */
    @Column(nullable = false, name = "estat_localitat")
    @Enumerated(EnumType.STRING)
    private EstatLocalitat estatLocalitat;

    /**
     * Población de la localización. Este campo no puede ser nulo y se mapea a la columna "poblacio" en la base de datos.
     */
    @Column(nullable = false)
    private String poblacio;

    /**
     * Dirección de la localización. Este campo no puede ser nulo y se mapea a la columna "direccio" en la base de datos.
     */
    @Column(nullable = false)
    private String direccio;

    /**
     * Nombre del local dentro de la localización. Este campo no puede ser nulo y se mapea a la columna "local" en la base de datos.
     */
    @Column(nullable = false)
    private String local;

    /**
     * Hora de apertura de la localización. Este campo no puede ser nulo y se mapea a la columna "horari_apertura" en la base de datos.
     */
    @Column(nullable = false, name = "horari_apertura")
    private LocalTime horariApertura;

    /**
     * Hora de cierre de la localización. Este campo no puede ser nulo y se mapea a la columna "horari_tancada" en la base de datos.
     */
    @Column(nullable = false, name = "horari_tancada")
    private LocalTime horariTancada;

    /**
     * Lista de vehículos asociados a la localización. Este campo establece una relación {@code OneToMany} con la entidad {@code Vehicle}.
     * El campo "localitat" de la tabla "vehicle" mapea con la clave primaria "codi_postal_loc" de la tabla "localitat".
     * Este campo se excluye de los métodos {@code toString()} y {@code equals()} para evitar referencias circulares.
     *
     * @see Vehicle
     */
    @OneToMany(mappedBy = "localitat")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Agente asociado a la localización. Este campo establece una relación {@code OneToOne} con la entidad {@code Agent}.
     * La relación es bidireccional, es decir, la entidad {@code Agent} también tiene una referencia a {@code Localitat}.
     * Este campo se excluye de los métodos {@code toString()} y {@code equals()} para evitar referencias circulares.
     *
     * @see Agent
     */
    @OneToOne(mappedBy = "localitat")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Agent agent;
}