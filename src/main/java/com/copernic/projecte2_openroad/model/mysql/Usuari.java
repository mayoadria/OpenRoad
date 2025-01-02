package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.model.enums.Pais;

// Jakarta
import com.copernic.projecte2_openroad.security.TipusPermis;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// Lombok
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
    @Id
    @NotNull(message = "El DNI és obligatori.")
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "El DNI ha de tenir 8 números seguits d'una lletra.")
    private String dni;

    // Camps Generals
    @Column(nullable = false)
    @NotNull(message = "El nom és obligatori.")
    @Size(min = 2, max = 50, message = "El nom ha de tenir entre 2 i 50 caràcters.")
    private String nom;

    @Column(nullable = false, name = "cognom_1")
    @NotNull(message = "El primer cognom és obligatori.")
    @Size(min = 2, max = 50, message = "El cognom ha de tenir entre 2 i 50 caràcters.")
    private String cognom1;

    @Column(nullable = true, name = "cognom_2")
    @Size(max = 50, message = "El segon cognom no pot superar els 50 caràcters.")
    private String cognom2;

    @Column(nullable = false, name = "num_contacte_1")
    @NotNull(message = "El número de contacte és obligatori.")
    @Min(value = 100000000, message = "El número de contacte ha de tenir 9 dígits.")
    @Max(value = 999999999, message = "El número de contacte ha de tenir 9 dígits.")
    private int numContacte1;

    @Column(nullable = true, name = "codi_postal")
    @Pattern(regexp = "\\d{5}", message = "El codi postal ha de tenir 5 dígits.")
    private String codiPostal;

    @Column(nullable = true)
    @Size(max = 100, message = "L'adreça no pot superar els 100 caràcters.")
    private String adreca;

    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    // Inici Sessió General
    @Column(nullable = false)
    @NotNull(message = "El correu electrònic és obligatori.")
    @Email(message = "El format del correu electrònic no és vàlid.")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "La contrasenya és obligatòria.")
    @Size(min = 8, message = "La contrasenya ha de tenir almenys 8 caràcters.")
    private String contrasenya;

    @Column(nullable = false, name = "nom_usuari")
    @NotNull(message = "El nom d'usuari és obligatori.")
    @Size(min = 4, max = 20, message = "El nom d'usuari ha de tenir entre 4 i 20 caràcters.")
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