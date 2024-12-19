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
import lombok.AllArgsConstructor;
import lombok.Data;

// Lombok
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatReserva;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    // PK ID_RESERVA - Identificador de Reserva.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    // Camps Generals
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = true, name = "data_inici")
    private LocalDate dataInici;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = true, name = "data_final")
    private LocalDate dataFinal;
    @Column(nullable = false, name = "preu_complert")
    private Double preuComplert;

//    // Enums
//    @Column(nullable = false, name = "estat_reserva")
//    @Enumerated(EnumType.STRING)
//    private EstatReserva estatReserva;

    // Relació ManyToOne amb taula - Client (Bidireccional)
    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "dni")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    // Relació ManyToOne amb taula - Vehicle (Bidireccional)
    @ManyToOne
    @JoinColumn(name = "vehicle", referencedColumnName = "matricula")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}