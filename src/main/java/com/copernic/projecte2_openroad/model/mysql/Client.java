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



/**
 * Representa un cliente en el sistema, el cual hereda de la clase {@link Usuari}.
 * La clase {@code Client} es una entidad que mantiene una relación {@link OneToMany} bidireccional
 * con la entidad {@link Reserva}, indicando que un cliente puede tener varias reservas asociadas.
 *
 * La estrategia de herencia utilizada es {@link InheritanceType#JOINED}, lo que significa que
 * los atributos comunes se guardan en una tabla base, mientras que los atributos específicos de
 * las subclases se guardan en tablas separadas.
 *
 */
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia JOINED
@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
@SuperBuilder // Permite el patrón de diseño Builder para la clase y sus clases padre
@EqualsAndHashCode(callSuper = true) // Genera equals y hashCode, incluyendo los atributos de la clase padre
@ToString(callSuper = true) // Genera el método toString, incluyendo los atributos de la clase padre
public class Client extends Usuari {

    /**
     * Relación {@link OneToMany} bidireccional con la entidad {@link Reserva}.
     * Un cliente puede tener varias reservas, y cada reserva está asociada a un cliente.
     *
     * @see Reserva
     */
    @OneToMany(mappedBy = "client")
    @ToString.Exclude // Excluye la propiedad del método toString
    @EqualsAndHashCode.Exclude // Excluye la propiedad del método equals y hashCode
    private List<Reserva> reserva;

    /**
     * Obtiene la contraseña del cliente.
     *
     * @return La contraseña del cliente.
     */
    @Override
    public String getPassword() {
        return getContrasenya();
    }

    /**
     * Obtiene el nombre de usuario del cliente.
     *
     * @return El nombre de usuario del cliente.
     */
    @Override
    public String getUsername() {
        return getNomUsuari();
    }
}
