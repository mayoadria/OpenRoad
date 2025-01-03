package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "admin")
public class Admin extends Agent{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codi_postal_loc", foreignKey = @ForeignKey(name = "fk_agent_localitat"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Localitat localitat;
    @Override
    public String getPassword() {
        return getContrasenya();
    }

    @Override
    public String getUsername() {
        return getNomUsuari();
    }
}
