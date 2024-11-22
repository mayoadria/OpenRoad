package model;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    // Inici Sessió General
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contrasenya;
    @Column(nullable = true, name = "nom_usuari")
    private String nomUsuari;

    // Camps Opcionals
    @Column(nullable = true, name = "num_contacte_2")
    private int numContacte2;
    @Column(nullable = true, name = "codi_postal")
    private String codiPostal;
    @Column(nullable = true)
    private String adreca;
}