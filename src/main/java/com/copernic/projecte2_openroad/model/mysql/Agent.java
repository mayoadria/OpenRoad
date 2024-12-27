package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.*;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;



@Entity
@Table(name = "agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Agent extends Client {
    // Relació OneToOne amb taula - Localitat
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codi_postal_loc", foreignKey = @ForeignKey(name = "fk_agent_localitat"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;

}