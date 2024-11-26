package com.copernic.projecte2_openroad.model.mysql;

// Java
import java.time.LocalDate;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

// Lombok
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatReserva;

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
    @Column(nullable = false, name = "data_inici")
    private LocalDate dataInici;
    @Column(nullable = false, name = "data_final")
    private LocalDate dataFinal;
    @Column(nullable = false, name = "preu_complet")
    private Double preuComplet;

    // Enums
    @Column(nullable = false, name = "estat_reserva")
    @Enumerated(EnumType.STRING)
    private EstatReserva estatReserva;

    // Relació OneToOne amb taula - Client (Bidireccional)
    @OneToOne
    @JoinColumn(name = "dni_client", foreignKey = @ForeignKey(name = "fk_reserva_agent"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    // Relació OneToOne amb taula - Vehicle (Bidireccional)
    @OneToOne
    @JoinColumn(name = "matricula", foreignKey = @ForeignKey(name = "fk_vehicle_reserva"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}