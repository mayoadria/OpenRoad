package model;

// Jakarta
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Enums
import model.enums.Reputacio;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Client extends Usuari {

    // Camps Generals
    @Column(nullable = false)
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