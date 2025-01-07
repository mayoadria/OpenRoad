package com.copernic.projecte2_openroad.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una imagen en el sistema. Esta clase es una entidad JPA que se mapea a la tabla "images" en la base de datos.
 * La entidad {@code Imagen} almacena información sobre el nombre, tipo y los datos binarios de la imagen.
 *
 * @see javax.persistence.Entity
 * @see javax.persistence.Table
 * @see javax.persistence.Id
 * @see javax.persistence.GeneratedValue
 * @see javax.persistence.GenerationType
 * @see javax.persistence.Lob
 */
@Entity
@Table(name = "images")
@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
public class Imagen {

    /**
     * Identificador único de la imagen en la base de datos.
     * Este campo es la clave primaria de la tabla.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autogeneración del valor del ID
    private Long id;

    /**
     * Nombre de la imagen, usado para identificar la imagen dentro del sistema.
     * Este campo se mapea a una columna "nombre" en la tabla de base de datos.
     */
    private String nombre;

    /**
     * Tipo de la imagen (por ejemplo, "image/jpeg", "image/png").
     * Este campo se mapea a una columna "type" en la tabla de base de datos.
     */
    private String type;

    /**
     * Datos binarios de la imagen, almacenados en la base de datos como un tipo {@code BLOB}.
     * Este campo se mapea a una columna "imageData" de tipo {@link Lob} en la base de datos.
     *
     * @see javax.persistence.Lob
     */
    @Lob
    private byte[] imageData;
}
