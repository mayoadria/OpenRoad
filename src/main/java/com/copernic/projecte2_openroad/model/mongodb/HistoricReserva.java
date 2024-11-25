package com.copernic.projecte2_openroad.model.mongodb;

// Java
import java.time.LocalDate;

// MongoDB
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Enums
import com.copernic.projecte2_openroad.model.enums.EstatReserva;

// Models
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricReserva {

    @Id
    private Long idReserva;

    private LocalDate dataInici;
    private LocalDate dataFinal;
    private Float preuComplet;
    private EstatReserva estatReserva;
    private Client client;
    private Vehicle vehicle;
}
