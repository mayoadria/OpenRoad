package com.copernic.projecte2_openroad.model.mysql;


// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

// Enums
import com.copernic.projecte2_openroad.model.enums.Reputacio;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Client extends Usuari {

    // Enums
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Reputacio reputacio;

    // Camps Documentació
    @Column(nullable = true, name = "carnet_conduir_doc")
    private String carnetConduirDoc;
    @Column(nullable = true, name = "tarjeta_credit_doc")
    private String tarjetaCreditDoc;

    // Relació OneToOne amb taula - Reserva (Bidireccional)
    @OneToOne(mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Reserva reserva;
}