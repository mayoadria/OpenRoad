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


@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    // PK MATRICULA - Identificador de Vehicle.
    @NotNull(message = "La matrícula no puede estar vacía.")
    // Validación del formato de la matrícula
    @Pattern(regexp = "^\\d{4}-[A-Z]{3}$",
            message = "El formato de la matrícula debe ser 'ABC-1234' o '1234-ABC'.")
    @Id
    private String matricula;

    @NotEmpty(message = "La marca no puede estar vacía.")
    // Camps Generals
    @Column(nullable = false)
    private String marca;
    @NotEmpty(message = "El modelo no puede estar vacía.")
    @Column(nullable = false)
    private String model;


    @Column(nullable = false, name = "preu_dia")
    private Double preuDia;
    @Column(nullable = false)
    private Double fianca;
    @Column(nullable = false, name = "dies_lloguer_minim")
    private int diesLloguerMinim;
    @Column(nullable = false, name = "dies_lloguer_maxim")
    private int diesLloguerMaxim;


    // Enums
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Places places;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Portes portes;
    @Column(nullable = false, name = "caixa_canvis")
    @Enumerated(EnumType.STRING)
    private CaixaCanvis caixaCanvis;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Marxes marxes;
    @Enumerated(EnumType.STRING)
    @Column(name = "combustible")
    private Combustible combustible;
    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;
    @Enumerated(EnumType.STRING)
    @Column(name = "estat_vehicle")
    private EstatVehicle estatVehicle;

    // Camps Opcionals
    @Column(nullable = true, name = "any_vehicle")
    private int anyVehicle;
    @Column(nullable = true)
    private int km;
    @Lob
    private String imageUrl;

    // Relació OneToMany amb taula - Reserva (Bidireccional)
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reserva> reserva;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen image;

    // Relació OneToMany amb taula - Incidencia
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Incidencia> incidencies = new ArrayList<>();

    // Relació ManyToOne amb taula - Localització
    @ManyToOne
    @JoinColumn(name = "localitat", referencedColumnName = "codi_postal_loc")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;
}