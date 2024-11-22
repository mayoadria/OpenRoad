package model;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import model.enums.Rol;

@Entity
@Table(name = "agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Agent extends Usuari {

    // Camps Generals
    @Column(nullable = false)
    private Rol rol;

    // Relació OneToOne amb taula - Localitat
    @OneToOne
    @JoinColumn(name = "codi_postal_loc", foreignKey = @ForeignKey(name = "fk_agent_localitat"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;
}