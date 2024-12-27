package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.*;

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
