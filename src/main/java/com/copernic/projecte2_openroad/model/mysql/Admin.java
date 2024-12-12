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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Enums
import com.copernic.projecte2_openroad.model.enums.Reputacio;

@Entity
@Table(name = "admin")
public class Admin extends Usuari{


    @Override
    public String getPassword() {
        return getContrasenya();
    }

    @Override
    public String getUsername() {
        return getNomUsuari();
    }
}
