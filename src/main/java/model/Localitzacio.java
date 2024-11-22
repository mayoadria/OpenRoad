package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "localitzacio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localitzacio {

    // PK CODIPOSTAL - Identificador de Localització.
    @Id
    @Column(nullable = false, name = "codi_postal")
    private String codiPostal;

    @Column(nullable = false)
    private String poblacio;
    @Column(nullable = false)
    private String direccio;

    // Camps Opcionals
    @Column(nullable = false)
    private String condicions;
    @Column(nullable = false)
    private LocalDateTime horari;


    // Relació OneToMany amb taula - Vehicle
    @OneToMany(mappedBy = "localitzacio")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

    // Relació OneToOne amb taula - Agent (Bidireccional)
    @OneToOne
    @JoinColumn(name = "dni_agent", foreignKey = @ForeignKey(name = "fk_localitat_agent"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Agent agent;
}
