package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.*;

/**
 * Representa a un administrador que hereda de la clase {@link Agent}.
 * La clase {@code Admin} sobrescribe los métodos {@code getPassword} y {@code getUsername}
 * para proporcionar acceso a las propiedades de contraseña y nombre de usuario específicas
 * del administrador.
 *
 * @see Agent
 * @see javax.persistence.Entity
 * @see javax.persistence.Table
 */
@Entity
@Table(name = "admin")
public class Admin extends Agent {

    /**
     * Obtiene la contraseña del administrador.
     * Sobrescribe el método {@link Agent#getPassword()} para acceder a la propiedad
     * de contraseña específica de la clase {@code Admin}.
     *
     * @return La contraseña del administrador.
     */
    @Override
    public String getPassword() {
        return getContrasenya();
    }

    /**
     * Obtiene el nombre de usuario del administrador.
     * Sobrescribe el método {@link Agent#getUsername()} para acceder a la propiedad
     * de nombre de usuario específica de la clase {@code Admin}.
     *
     * @return El nombre de usuario del administrador.
     */
    @Override
    public String getUsername() {
        return getNomUsuari();
    }
}

