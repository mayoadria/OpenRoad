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

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Usuari implements UserDetails {

    // PK DNI - Identificador d'Usuari (Client i Agent).
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 9 ,max =9,message = "Tiene que tener un tamaño de 9 letras")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener 8 números seguidos de una letra mayúscula.")
    @Id
    private String dni;

    // Camps Generals
    @NotEmpty(message = "Debes entrar un nombre valido")
    @Column(nullable = false)
    private String nom;

    @NotEmpty(message = "Debes entrar un apellido valido")
    @Column(nullable = false, name = "cognom_1")
    private String cognom1;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "\\d{9}", message = "El número de teléfono solo puede contener dígitos")
    @Column(nullable = false, name = "num_contacte_1")
    private String numContacte1;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 5, max = 5, message = "El codi postal debe tener exactamente 5 dígitos")
    @Pattern(regexp = "\\d{5}", message = "El codi postal solo puede contener dígitos")
    @Column(nullable = true, name = "codi_postal")
    private String codiPostal;

    @NotEmpty(message = "L'Adreça no pot ser buida")
    @Column(nullable = true)
    private String adreca;

    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    // Inici Sessió General
    @NotEmpty(message = "No puede estar vacio")
    @Column(nullable = false)
    private String email;
    @NotEmpty(message = "No puede estar vacio")
    @Column(nullable = false)
    private String contrasenya;
    @Column(nullable = false, name = "nom_usuari")
    private String nomUsuari;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private String permisos;

    @Lob
    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    private Imagen image;

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