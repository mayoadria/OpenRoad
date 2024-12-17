package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.model.enums.Pais;

// Jakarta
import com.copernic.projecte2_openroad.security.TipusPermis;
import jakarta.persistence.*;

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
    private String dni;

    // Camps Generals
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false, name = "cognom_1")
    private String cognom1;
    @Column(nullable = true, name = "cognom_2")
    private String cognom2;
    @Column(nullable = false, name = "num_contacte_1")
    private int numContacte1;
    @Column(nullable = true, name = "codi_postal")
    private String codiPostal;
    @Column(nullable = true)
    private String adreca;

    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    // Inici Sessió General
    @Column(nullable = false)
    private String email;
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