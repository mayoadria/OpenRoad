package com.copernic.projecte2_openroad.model.mysql;


// Jakarta
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;



@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Client extends Usuari {

    // Relació OneToMany amb taula - Reserva (Bidireccional)
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reserva> reserva;

    @Override
    public String getPassword() {
        return getContrasenya();
    }

    @Override
    public String getUsername() {
        return getNomUsuari();
    }
}
