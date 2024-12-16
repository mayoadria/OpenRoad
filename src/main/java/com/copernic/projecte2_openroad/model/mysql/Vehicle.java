package com.copernic.projecte2_openroad.model.mysql;

// Java
import java.util.ArrayList;
import java.util.List;

// Jakarta
import com.copernic.projecte2_openroad.model.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Lombok
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
    @Id
    private String matricula;

    // Camps Generals
    @Column(nullable = false)
    private String marca;
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

    // Camps Opcionals
    @Column(nullable = true, name = "any_vehicle")
    private int anyVehicle;
    @Column(nullable = true)
    private int km;
    @Column(nullable = true, name = "desc_vehicle")
    private String descVehicle;
    

    // Relació OneToMany amb taula - Reserva (Bidireccional)
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reserva> reserva;

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