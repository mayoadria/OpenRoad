package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

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
        return reservaRepoMongo.findById(id).get();
    }

    // Llistar tots els Reservas.
    public List<HistoricReserva> llistarReservas() {
        return reservaRepoMongo.findAll();
    }

    // Modificar Reserva.
    public String modificarReserva(HistoricReserva reserva) {
        try {
            if (llistarReservaPerId(reserva.getIdReserva()) != null) {
                reservaRepoMongo.save(reserva);
                String msg = "Reserva: " + reserva.getDataInici() + " amb ID(" + reserva.getIdReserva() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Reserva: ID(" + reserva.getIdReserva() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Reserva: ID(" + reserva.getIdReserva() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Reserva.
    public String eliminarReservaPerId(String id) {
        HistoricReserva Reserva = llistarReservaPerId(id);
        try {
            if (Reserva != null) {
                reservaRepoMongo.delete(Reserva);
                String msg = "Reserva: " + Reserva.getDataInici() + " amb ID(" + Reserva.getIdReserva() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Reserva: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Reserva: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
