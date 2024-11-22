package model;

// Java
import java.time.LocalDate;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import model.enums.EstatIncidencia;

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

    // Camps Generals
    @Column(nullable = false, name = "estat_incidencia")
    private EstatIncidencia estatIncidencia;
    @Column(nullable = false)
    private String motiu;
    @Column(nullable = false)
    private Float cost;
    @Column(nullable = false, name = "data_inici")
    private LocalDate dataInici;

    // Camps Opcionals
    @Column(nullable = true, name = "data_final")
    private LocalDate dataFinal;
    @Column(nullable = true, name = "incidencia_doc")
    private String incidenciaDoc;

    // Relació ManyToOne amb taula - Vehicle
    @ManyToOne
    @JoinTable(name = "incidencia_vehicle", joinColumns = @JoinColumn(name = "id_incidencia", referencedColumnName = "id_incidencia"), inverseJoinColumns = @JoinColumn(name = "matricula", referencedColumnName = "matricula"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}