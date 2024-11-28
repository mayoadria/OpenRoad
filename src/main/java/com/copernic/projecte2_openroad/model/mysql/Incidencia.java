package com.copernic.projecte2_openroad.model.mysql;

// Java
import java.time.LocalDate;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;

@Entity
@Table(name = "incidencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incidencia {

    // PK ID_INCIDENCIA - Identificador de Incidencia.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Long idIncidencia;

    // Enums
    @Column(nullable = false, name = "estat_incidencia")
    @Enumerated(EnumType.STRING)
    private EstatIncidencia estatIncidencia;

    // Camps Generals
    @Column(nullable = false)
    private String titolInc;
    @Column(nullable = false)
    private String motiu;
    @Column(nullable = false)
    private Double cost;
    @Column(nullable = false, name = "data_inici")
    private LocalDate dataInici;

    // Camps Opcionals
    @Column(nullable = true, name = "data_final")
    private LocalDate dataFinal;
    @Column(nullable = true, name = "incidencia_doc")
    private String incidenciaDoc;

    // Relació ManyToOne amb taula - Vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle", referencedColumnName = "matricula")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}