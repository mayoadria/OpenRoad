package com.copernic.projecte2_openroad.model.mysql;

// Java
import java.util.ArrayList;
import java.util.List;

// Jakarta
import com.copernic.projecte2_openroad.model.enums.*;
import jakarta.persistence.*;

// Lombok
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums


/**
 * Clase que representa un vehículo en el sistema. Esta clase contiene información detallada sobre los vehículos disponibles
 * para ser alquilados, incluyendo atributos como la matrícula, marca, modelo, precio de alquiler, entre otros.
 * Además, gestiona relaciones con otras entidades como {@link Reserva}, {@link Imagen}, {@link Incidencia}, y {@link Localitat}.
 *
 * @see Reserva
 * @see Imagen
 * @see Incidencia
 * @see Localitat
 */
@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    /**
     * Matrícula del vehículo. Es el identificador único del vehículo en el sistema.
     * El formato de la matrícula debe ser '1234-ABC' o 'ABC-1234'.
     *
     * @see #matricula
     */
    @NotNull(message = "La matrícula no puede estar vacía.")
    @Pattern(regexp = "^\\d{4}-[A-Z]{3}$", message = "El formato de la matrícula debe ser 'ABC-1234' o '1234-ABC'.")
    @Id
    private String matricula;

    /**
     * Marca del vehículo. Este campo no puede estar vacío.
     */
    @NotEmpty(message = "La marca no puede estar vacía.")
    @Column(nullable = false)
    private String marca;

    /**
     * Modelo del vehículo. Este campo no puede estar vacío.
     */
    @NotEmpty(message = "El modelo no puede estar vacía.")
    @Column(nullable = false)
    private String model;

    /**
     * Precio de alquiler por día del vehículo. Este campo no puede ser nulo.
     */
    @Column(nullable = false, name = "preu_dia")
    private Double preuDia;

    /**
     * Fianza asociada al alquiler del vehículo. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private Double fianca;

    /**
     * Número mínimo de días de alquiler para este vehículo.
     */
    @Column(nullable = false, name = "dies_lloguer_minim")
    private int diesLloguerMinim;

    /**
     * Número máximo de días de alquiler para este vehículo.
     */
    @Column(nullable = false, name = "dies_lloguer_maxim")
    private int diesLloguerMaxim;

    /**
     * Tipo de plazas del vehículo, definido por el enum {@link Places}.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Places places;

    /**
     * Número de puertas del vehículo, definido por el enum {@link Portes}.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Portes portes;

    /**
     * Tipo de caja de cambios del vehículo, definido por el enum {@link CaixaCanvis}.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false, name = "caixa_canvis")
    @Enumerated(EnumType.STRING)
    private CaixaCanvis caixaCanvis;

    /**
     * Marca de las marchas del vehículo, definida por el enum {@link Marxes}.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Marxes marxes;

    /**
     * Tipo de combustible del vehículo, definido por el enum {@link Combustible}.
     * Este campo puede ser nulo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "combustible")
    private Combustible combustible;

    /**
     * Color del vehículo, definido por el enum {@link Color}.
     * Este campo puede ser nulo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    /**
     * Estado actual del vehículo, definido por el enum {@link EstatVehicle}.
     * Este campo no puede ser nulo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estat_vehicle")
    private EstatVehicle estatVehicle;

    /**
     * Año de fabricación del vehículo. Este campo es opcional.
     */
    @Column(nullable = true, name = "any_vehicle")
    private int anyVehicle;

    /**
     * Kilometraje actual del vehículo. Este campo es opcional.
     */
    @Column(nullable = true)
    private int km;

    /**
     * URL de la imagen del vehículo. Este campo puede ser nulo si el vehículo no tiene imagen asociada.
     */
    @Lob
    private String imageUrl;

    /**
     * Relación bidireccional con la tabla {@link Reserva}. Un vehículo puede tener muchas reservas asociadas.
     */
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reserva> reserva;

    /**
     * Relación uno a uno con la tabla {@link Imagen}. Un vehículo puede tener una imagen asociada.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen image;

    /**
     * Relación uno a muchos con la tabla {@link Incidencia}. Un vehículo puede tener muchas incidencias asociadas.
     */
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Incidencia> incidencies = new ArrayList<>();

    /**
     * Relación muchos a uno con la tabla {@link Localitat}. Cada vehículo está asociado a una localidad.
     */
    @ManyToOne
    @JoinColumn(name = "localitat", referencedColumnName = "codi_postal_loc")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;
}