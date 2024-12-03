package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.model.enums.Pais;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Usuari {

    // PK DNI - Identificador de Usuari (Client i Agent).
    @Id
    private String dni;

    // Camps Generals
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false, name = "cognom_1")
    private String cognom1;
    @Column(nullable = false, name = "cognom_2")
    private String cognom2;
    @Column(nullable = false, name = "num_contacte_1")
    private int numContacte1;
    @Column(nullable = true, name = "codi_postal")
    private String codiPostal;
    @Column(nullable = false)
    private String adreca;
    @Column(nullable = true)
    private Pais pais;

    // Inici Sessió General
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contrasenya;
    @Column(nullable = false, name = "nom_usuari")
    private String nomUsuari;
    
}