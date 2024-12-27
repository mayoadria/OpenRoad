package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;
import java.util.Optional;

// Spring
import com.copernic.projecte2_openroad.model.enums.EstatReserva;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
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

    public List<Reserva> findReservasByClient_nom(String username) {
        // Supongamos que el repositorio tiene un método para buscar por nombre de usuario
        return reservaRepoSQL.findByClient_nom(username);
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

    public Optional<Reserva> buscarReservaPorVehiculo(Vehicle vehicle) {
        return reservaRepoSQL.findByVehicle(vehicle);
    }

    public List<Reserva> buscarReservaPerDNI(String dni) {
        return reservaRepoSQL.findByClient_dni(dni);
    }

    public void activarReserva(Long id) {
        Reserva user = llistarReservaPerId(id);
        user.setEstatReserva(EstatReserva.ACCEPTADA);
        reservaRepoSQL.save(user);
    }

}
