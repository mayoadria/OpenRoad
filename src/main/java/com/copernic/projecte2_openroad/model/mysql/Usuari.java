package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.model.enums.Pais;

// Jakarta
import com.copernic.projecte2_openroad.security.TipusPermis;
import jakarta.persistence.*;

// Lombok
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase abstracta que representa a un usuario en el sistema. Esta clase es la base para las entidades {@link Client}
 * y {@link Agent}, y contiene los atributos y comportamientos comunes a ambos tipos de usuario.
 * Implementa la interfaz {@link UserDetails} de Spring Security para gestionar la autenticación y autorización.
 *
 * @see Client
 * @see Agent
 * @see UserDetails
 * @see Permisos
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Usuari implements UserDetails {

    /**
     * Identificador único del usuario, que en este caso es el DNI del usuario.
     * Debe tener un tamaño exacto de 9 caracteres, compuestos por 8 números seguidos de una letra mayúscula.
     *
     * @see #dni
     */
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 9, max = 9, message = "Tiene que tener un tamaño de 9 letras")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener 8 números seguidos de una letra mayúscula.")
    @Id
    private String dni;

    /**
     * Nombre del usuario. Este campo no puede estar vacío.
     */
    @NotEmpty(message = "Debes entrar un nombre valido")
    @Column(nullable = false)
    private String nom;

    /**
     * Primer apellido del usuario. Este campo no puede estar vacío.
     */
    @NotEmpty(message = "Debes entrar un apellido valido")
    @Column(nullable = false, name = "cognom_1")
    private String cognom1;

    /**
     * Número de contacto del usuario. Este campo debe contener exactamente 9 dígitos.
     */
    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "\\d{9}", message = "El número de teléfono solo puede contener dígitos")
    @Column(nullable = false, name = "num_contacte_1")
    private String numContacte1;

    /**
     * Código postal del usuario, con una longitud exacta de 5 dígitos.
     */
    @Size(min = 5, max = 5, message = "El codi postal debe tener exactamente 5 dígitos")
    @Pattern(regexp = "\\d{5}", message = "El codi postal solo puede contener dígitos")
    @Column(nullable = true, name = "codi_postal")
    private String codiPostal;

    /**
     * Dirección del usuario.
     */
    @Column(nullable = true)
    private String adreca;

    /**
     * País del usuario. Relación con la entidad {@link Pais}.
     */
    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    /**
     * Correo electrónico del usuario. Este campo no puede estar vacío y debe ser único.
     */
    @NotEmpty(message = "No puede estar vacio")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Contraseña del usuario. Este campo no puede estar vacío.
     */
    @NotEmpty(message = "No puede estar vacio")
    @Column(nullable = false)
    private String contrasenya;

    /**
     * Nombre de usuario único utilizado para la autenticación en el sistema.
     * Este campo no puede estar vacío.
     */
    @Column(nullable = false, name = "nom_usuari")
    private String nomUsuari;

    /**
     * Indica si el usuario está habilitado para acceder al sistema.
     * El valor predeterminado es {@code false}.
     */
    @Column(nullable = false)
    private boolean enabled = false;

    /**
     * Permisos del usuario, representados como una cadena de texto separada por comas.
     * Estos permisos serán procesados para asignar los roles adecuados al usuario.
     */
    @Column(nullable = false)
    private String permisos;

    /**
     * URL de la imagen del usuario.
     * Este campo puede ser nulo si el usuario no tiene una imagen asociada.
     */
    @Lob
    private String imageUrl;

    /**
     * Imagen asociada al usuario. Relación con la entidad {@link Imagen}.
     * Este campo es opcional.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen image;

    /**
     * Obtiene las autoridades (permisos) del usuario.
     * Este método es parte de la implementación de la interfaz {@link UserDetails} de Spring Security.
     * La cadena de permisos se divide y se asigna a los roles correspondientes para la autenticación.
     *
     * @return una colección de {@link GrantedAuthority} que representan los permisos del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> ret = new ArrayList<>();
        String[] llista1 = permisos.split(",");

        for (String p : llista1) {
            ret.add(new Permisos(TipusPermis.valueOf(p)));
        }
        return ret;
    }
}