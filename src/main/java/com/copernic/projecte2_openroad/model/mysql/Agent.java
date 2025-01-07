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


/**
 * Representa a un agente que hereda de la clase {@link Client}.
 * La clase {@code Agent} define una relación de tipo {@link OneToOne} con la entidad {@link Localitat},
 * representando una asociación entre un agente y una localización.
 *
 * Utiliza Lombok para generar automáticamente métodos como getters, setters, constructores, y métodos
 * de comparación y representación en forma de cadena.
 *
 * @see Client
 * @see Localitat
 * @see javax.persistence.Entity
 * @see javax.persistence.Table
 * @see javax.persistence.OneToOne
 * @see javax.persistence.JoinColumn
 */
@Entity
@Table(name = "agent")
@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
@SuperBuilder // Permite el patrón de diseño Builder para la clase y sus clases padre
@EqualsAndHashCode(callSuper = true) // Genera equals y hashCode, incluyendo los atributos de la clase padre
@ToString(callSuper = true) // Genera el método toString, incluyendo los atributos de la clase padre
public class Agent extends Client {

    /**
     * Relación de tipo {@link OneToOne} con la entidad {@link Localitat}.
     * Un agente puede estar asociado con una localización específica.
     * La relación está mapeada por la columna {@code codi_postal_loc} en la tabla de base de datos.
     *
     * @see Localitat
     */
    @OneToOne
    @JoinColumn(name = "codi_postal_loc", foreignKey = @ForeignKey(name = "fk_agent_localitat"), nullable = true)
    @ToString.Exclude // Excluye la propiedad del método toString
    @EqualsAndHashCode.Exclude // Excluye la propiedad del método equals y hashCode
    private Localitat localitat;
}
