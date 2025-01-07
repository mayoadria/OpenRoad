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

/**
 * Representa una reserva de un vehículo realizada por un cliente. Esta entidad está mapeada a la tabla {@code reserva}
 * en la base de datos y contiene información sobre las fechas de recogida y entrega, el precio total, el estado de la
 * reserva, y las relaciones con el cliente y el vehículo asociado a la reserva.
 *
 * @see Client
 * @see Vehicle
 * @see EstatReserva
 */
@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    /**
     * Identificador único de la reserva.
     * Este campo es la clave primaria de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    /**
     * Fecha de inicio o recogida de la reserva.
     * Representa la fecha en que el cliente recogerá el vehículo.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "fechaInici")
    private LocalDate fechaRecogida;

    /**
     * Fecha de finalización o entrega de la reserva.
     * Representa la fecha en que el cliente devolverá el vehículo.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "fechaFinal")
    private LocalDate fechaEntrega;

    /**
     * Precio total de la reserva.
     * Es el precio calculado para la duración de la reserva, incluyendo impuestos y cargos adicionales.
     */
    @Column(nullable = false, name = "preu_complert")
    private Double preuComplert;

    /**
     * Estado actual de la reserva.
     * Este campo hace referencia al estado de la reserva, que es un valor de la enumeración {@link EstatReserva}.
     *
     * @see EstatReserva
     */
    @Column(nullable = false, name = "estat_reserva")
    @Enumerated(EnumType.STRING)
    private EstatReserva estatReserva;

    /**
     * Cliente asociado a la reserva.
     * Representa la relación entre la reserva y el cliente que realiza la reserva. Es una relación {@code ManyToOne}
     * con la entidad {@link Client}.
     *
     * @see Client
     */
    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "dni")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    /**
     * Vehículo asociado a la reserva.
     * Representa la relación entre la reserva y el vehículo que el cliente ha reservado. Es una relación {@code ManyToOne}
     * con la entidad {@link Vehicle}.
     *
     * @see Vehicle
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", referencedColumnName = "matricula")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;
}