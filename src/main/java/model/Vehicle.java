package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import model.enums.CaixaCanvis;
import model.enums.Marxes;
import model.enums.Places;
import model.enums.Portes;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    // PK MATRICULA - Identificador de Vehicle.
    @Id
    private String Matricula;

    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String combustible;
    @Column(nullable = false)
    private String color;

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

    // Camps Opcionals
    @Column(nullable = true)
    private LocalDate any;
    @Column(nullable = true)
    private int km;

    // Relació OneToOne amb taula - Reserva
    @OneToOne
    @JoinColumn(name = "IdReserva", foreignKey = @ForeignKey(name = "fk_vehicle_reserva"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Reserva reserva;

    // Relació OneToMany amb taula - Incidencia
    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Incidencia> incidencies = new ArrayList<>();

    // Relació ManyToOne amb taula - Localització
    @ManyToOne
    @JoinTable(name = "vehicle_localitzacio", joinColumns = @JoinColumn(name = "Matricula", referencedColumnName = "Matricula"), inverseJoinColumns = @JoinColumn(name = "CodiPostal", referencedColumnName = "CodiPostal"))
    @ToString.Exclude // Evita bucles infinits en el mètode toString()
    @EqualsAndHashCode.Exclude // Evita problemes d'igualtat basats en referències circulars
    private Localitzacio localitzacio;
}