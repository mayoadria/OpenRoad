package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;
import java.util.Optional;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.HistoricReserva;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.ReservaRepositoryMongo;

@Service
public class ReservaServiceMongo {

    @Autowired
    private ReservaRepositoryMongo reservaRepoMongo;

    // Crear Reserva.
    public String guardarReserva(HistoricReserva reserva) {
        try {
            reservaRepoMongo.save(reserva);
            String msg = "Reserva: " + reserva.getDataInici() + " amb ID(" + reserva.getIdReserva() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Reserva: ID(" + reserva.getIdReserva() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Llistar Reserva.
    public HistoricReserva llistarReservaPerId(String id) {
        Optional<HistoricReserva> reserva = reservaRepoMongo.findById(id);
        return reserva.orElse(null);
    }

    // Llistar totes les Reservas.
    public List<HistoricReserva> llistarReservas() {
        return reservaRepoMongo.findAll();
    }

    // Eliminar Reserva.
    public String eliminarReservaPerId(String id) {
        try {
            HistoricReserva reserva = llistarReservaPerId(id);
            if (reserva != null) {
                reservaRepoMongo.delete(reserva);
                String msg = "Reserva: " + reserva.getDataInici() + " amb ID(" + reserva.getIdReserva() + ") esborrat correctament!";
                return msg;
            } else {
                return "Reserva: ID(" + id + ") no s'ha trobat a la BD MongoDB!";
            }
        } catch (Exception e) {
            return "Error amb Reserva: ID(" + id + "). Excepció: " + e.getMessage();
        }
    }
}
