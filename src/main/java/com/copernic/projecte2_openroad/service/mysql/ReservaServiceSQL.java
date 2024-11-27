package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Reserva;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.ReservaRepositorySQL;

@Service
public class ReservaServiceSQL {

    @Autowired
    private ReservaRepositorySQL reservaRepoSQL;

    // Crear Reserva.
    public String guardarReserva(Reserva reserva) {
        try {
            reservaRepoSQL.save(reserva);
            String msg = "Reserva: " + reserva.getClient().getEmail() + " amb ID(" + reserva.getIdReserva() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Reserva: ID(" + reserva.getIdReserva() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Llistar Reserva.
    public Reserva llistarReservaPerId(Long id) {
        return reservaRepoSQL.findById(id).get();
    }

    // Llistar totes les Reservas.
    public List<Reserva> llistarReserves() {
        return reservaRepoSQL.findAll();
    }

    // Modificar Reserva.
    public String modificarReserva(Reserva reserva) {
        try {
            if (llistarReservaPerId(reserva.getIdReserva()) != null) {
                reservaRepoSQL.save(reserva);
                String msg = "Reserva: " + reserva.getClient().getEmail() + " amb ID(" + reserva.getIdReserva() + ") modificat correctament!";
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
    public String eliminarReservaPerId(Long id) {
        Reserva reserva = llistarReservaPerId(id);
        try {
            if (reserva != null) {
                reservaRepoSQL.delete(reserva);
                String msg = "Reserva: " + reserva.getClient().getEmail() + " amb ID(" + reserva.getIdReserva() + ") esborrat correctament!";
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
