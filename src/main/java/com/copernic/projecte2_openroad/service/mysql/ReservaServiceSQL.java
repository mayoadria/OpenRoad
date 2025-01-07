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

/**
 * Servicio encargado de gestionar las reservas en la base de datos.
 * Este servicio proporciona métodos para crear, listar, modificar, eliminar y consultar reservas.
 * Además, mantiene un historial de las reservas en una base de datos MongoDB a través del servicio {@link ReservaServiceMongo}.
 *
 * @see ReservaRepositorySQL
 * @see ReservaServiceMongo
 * @see Reserva
 * @see HistoricReserva
 */
@Service
public class ReservaServiceSQL {

    /**
     * Repositorio utilizado para interactuar con la base de datos SQL y gestionar las reservas.
     */
    @Autowired
    private ReservaRepositorySQL reservaRepoSQL;

    /**
     * Servicio utilizado para almacenar el historial de reservas en MongoDB.
     */
    @Autowired
    private ReservaServiceMongo reservaServiceMongo;

    /**
     * Guarda una nueva reserva en la base de datos SQL y en el historial de MongoDB.
     *
     * Este método recibe una reserva, la guarda en la base de datos SQL y también la guarda en MongoDB como un historial.
     *
     * @param reserva La reserva que se desea guardar.
     * @return Un mensaje indicando si la reserva fue creada exitosamente o si ocurrió un error.
     */
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

    /**
     * Obtiene una reserva de la base de datos por su ID.
     *
     * Este método consulta la base de datos SQL para obtener la reserva correspondiente al ID proporcionado.
     *
     * @param id El ID de la reserva que se desea recuperar.
     * @return La reserva correspondiente al ID o {@code null} si no se encuentra.
     */
    public Reserva llistarReservaPerId(Long id) {
        return reservaRepoSQL.findById(id).get();
    }

    /**
     * Encuentra todas las reservas asociadas a un cliente por el nombre del cliente.
     *
     * Este método consulta la base de datos SQL para obtener todas las reservas relacionadas con un cliente específico,
     * utilizando el nombre del cliente como filtro.
     *
     * @param username El nombre del cliente que se desea consultar.
     * @return Una lista con todas las reservas asociadas al cliente.
     */
    public List<Reserva> findReservasByClient_nom(String username) {
        return reservaRepoSQL.findByClient_nom(username);
    }

    /**
     * Obtiene todas las reservas almacenadas en la base de datos.
     *
     * Este método consulta la base de datos SQL y devuelve todas las reservas almacenadas.
     *
     * @return Una lista con todas las reservas en la base de datos.
     */
    public List<Reserva> llistarReserves() {
        return reservaRepoSQL.findAll();
    }

    /**
     * Modifica una reserva existente en la base de datos SQL y actualiza el historial en MongoDB.
     *
     * Este método recibe una reserva con los datos actualizados y la guarda nuevamente en la base de datos SQL.
     * También se actualiza la entrada correspondiente en el historial de MongoDB.
     *
     * @param reserva La reserva con los datos actualizados.
     * @return Un mensaje indicando si la reserva fue modificada exitosamente o si no se encontró la reserva en la base de datos.
     */
    public String modificarReserva(Reserva reserva) {
        try {
            if (llistarReservaPerId(reserva.getIdReserva()) != null) {
                reservaRepoSQL.save(reserva);
                guardarHistoric(reserva); // Actualizar en MongoDB
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

    /**
     * Elimina una reserva de la base de datos SQL y de MongoDB.
     *
     * Este método recibe el ID de la reserva, la elimina de la base de datos SQL y también elimina su registro en MongoDB
     * mediante el servicio {@link ReservaServiceMongo}.
     *
     * @param id El ID de la reserva que se desea eliminar.
     * @return Un mensaje indicando si la reserva fue eliminada exitosamente o si no se encontró.
     */
    public String eliminarReservaPerId(Long id) {
        Reserva reserva = llistarReservaPerId(id);
        try {
            if (reserva != null) {
                reservaRepoSQL.delete(reserva);
                reservaServiceMongo.eliminarReservaPerId(reserva.getIdReserva().toString()); // Eliminar de MongoDB
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

    /**
     * Busca una reserva por el vehículo asociado.
     *
     * Este método consulta la base de datos SQL para encontrar una reserva asociada a un vehículo específico.
     *
     * @param vehicle El vehículo asociado a la reserva que se desea encontrar.
     * @return Un {@link Optional} con la reserva encontrada o vacío si no se encuentra.
     */
    public Optional<Reserva> buscarReservaPorVehiculo(Vehicle vehicle) {
        return reservaRepoSQL.findByVehicle(vehicle);
    }

    /**
     * Busca todas las reservas asociadas a un cliente por su DNI.
     *
     * Este método consulta la base de datos SQL para encontrar todas las reservas asociadas a un cliente
     * utilizando su DNI como filtro.
     *
     * @param dni El DNI del cliente que se desea consultar.
     * @return Una lista con todas las reservas asociadas al cliente.
     */
    public List<Reserva> buscarReservaPerDNI(String dni) {
        return reservaRepoSQL.findByClient_dni(dni);
    }

    /**
     * Activa una reserva y la actualiza en la base de datos SQL y en MongoDB.
     *
     * Este método cambia el estado de una reserva a "ACEPTADA" y guarda los cambios en la base de datos SQL,
     * además de actualizar el historial en MongoDB.
     *
     * @param id El ID de la reserva que se desea activar.
     */
    public void activarReserva(Long id) {
        Reserva reserva = llistarReservaPerId(id);
        reserva.setEstatReserva(EstatReserva.ACCEPTADA);
        reservaRepoSQL.save(reserva);
        guardarHistoric(reserva); // Actualizar en MongoDB
    }

    /**
     * Guarda el historial de una reserva en MongoDB.
     *
     * Este método crea una entrada en el historial de MongoDB para la reserva proporcionada.
     *
     * @param reserva La reserva cuyo historial se desea guardar.
     */
    private void guardarHistoric(Reserva reserva) {
        HistoricReserva historic = new HistoricReserva();
        historic.setIdReserva(reserva.getIdReserva().toString());
        historic.setDataInici(reserva.getFechaRecogida());
        historic.setDataFinal(reserva.getFechaEntrega());
        historic.setPreuComplet(reserva.getPreuComplert());
        historic.setEstatReserva(reserva.getEstatReserva());
        historic.setDniClient(reserva.getClient().getDni());
        historic.setMatriculaVehicle(reserva.getVehicle().getMatricula());
        reservaServiceMongo.guardarReserva(historic);
    }
}
