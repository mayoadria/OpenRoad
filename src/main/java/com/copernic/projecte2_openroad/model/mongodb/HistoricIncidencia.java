package com.copernic.projecte2_openroad.model.mongodb;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "incidencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricIncidencia {

    @Id
    private String idIncidencia;

    private EstatIncidencia estatIncidencia;
    private String titolInc;
    private String motiu;
    private Double cost;
    private LocalDate dataInici;
    private LocalDate dataFinal;
    private String matriculaVehicle;

    private List<Byte> incidenciaDoc;
}
