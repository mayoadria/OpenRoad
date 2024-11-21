package model;

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
    private String CodiPostal;

    @Column(nullable = false)
    private String poblacio;

    // Camps Opcionals
    @Column(nullable = true)
    private String direccio;

    // Relació OneToMany amb taula - Vehicle
    @OneToMany(mappedBy = "localitzacio")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

    // Relació OneToOne amb taula - Agent
    @OneToOne
    @JoinColumn(name = "DNI", foreignKey = @ForeignKey(name = "fk_localitat_agent"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Agent agent;
}
