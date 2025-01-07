package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;
import java.util.Optional;

// Spring
import com.copernic.projecte2_openroad.model.enums.EstatReserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mongodb.HistoricReserva;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.ReservaRepositorySQL;

// MongoDB Service
import com.copernic.projecte2_openroad.service.mongodb.ReservaServiceMongo;

@Service
public class ReservaServiceSQL {

    @Autowired
    private ReservaRepositorySQL reservaRepoSQL;

    @Autowired
    private ReservaServiceMongo reservaServiceMongo;

    // Crear Reserva.
    public String guardarReserva(Reserva reserva) {
        try {
            reservaRepoSQL.save(reserva);
            guardarHistoric(reserva); // Guardar en MongoDB
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
                guardarHistoric(reserva); // Actualitzar en MongoDB
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
                reservaServiceMongo.eliminarReservaPerId(reserva.getIdReserva().toString()); // Esborrar de MongoDB
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
        Reserva reserva = llistarReservaPerId(id);
        reserva.setEstatReserva(EstatReserva.ACCEPTADA);
        reservaRepoSQL.save(reserva);
        guardarHistoric(reserva); // Actualitzar en MongoDB
    }

    // Mètode privat per guardar una reserva en l'historial de MongoDB
    private void guardarHistoric(Reserva reserva) {
        HistoricReserva historic = new HistoricReserva();
        historic.setIdReserva(reserva.getIdReserva().toString());
        historic.setDataInici(reserva.getFechaRecogida()); // Corregido según el nombre del campo
        historic.setDataFinal(reserva.getFechaEntrega()); // Corregido según el nombre del campo
        historic.setPreuComplet(reserva.getPreuComplert());
        historic.setEstatReserva(reserva.getEstatReserva());
        historic.setDniClient(reserva.getClient().getDni()); // Asumiendo que "dni" es un campo en Client
        historic.setMatriculaVehicle(reserva.getVehicle().getMatricula()); // Asumiendo que "matricula" es un campo en Vehicle
        reservaServiceMongo.guardarReserva(historic);
    }


}
