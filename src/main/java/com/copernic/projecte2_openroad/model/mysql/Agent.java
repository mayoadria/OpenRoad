package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.experimental.SuperBuilder;

// Enums
import com.copernic.projecte2_openroad.model.enums.Rol;

@Entity
@Table(name = "agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Agent extends Usuari {

    // Enums
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    // Relació OneToOne amb taula - Localitat
    @OneToOne
    @JoinColumn(name = "codi_postal_loc", foreignKey = @ForeignKey(name = "fk_agent_localitat"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;
}