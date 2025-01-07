/**
 * Servei que gestiona les operacions CRUD per a les reserves emmagatzemades en una col·lecció MongoDB.
 * Proporciona funcionalitats per crear, llistar i eliminar reserves.
 */
package com.copernic.projecte2_openroad.service.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mongodb.HistoricReserva;
import com.copernic.projecte2_openroad.repository.mongodb.ReservaRepositoryMongo;

/**
 * Classe de servei per a la gestió de les reserves a MongoDB.
 */
@Service
public class ReservaServiceMongo {

    @Autowired
    private ReservaRepositoryMongo reservaRepoMongo;

    /**
     * Guarda una nova reserva a MongoDB.
     *
     * @param reserva l'entitat de la reserva a guardar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
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

    /**
     * Cerca una reserva a MongoDB pel seu ID.
     *
     * @param id l'ID de la reserva a cercar.
     * @return l'entitat de la reserva si existeix, o null si no es troba.
     */
    public HistoricReserva llistarReservaPerId(String id) {
        Optional<HistoricReserva> reserva = reservaRepoMongo.findById(id);
        return reserva.orElse(null);
    }

    /**
     * Llista totes les reserves emmagatzemades a MongoDB.
     *
     * @return una llista amb totes les reserves.
     */
    public List<HistoricReserva> llistarReservas() {
        return reservaRepoMongo.findAll();
    }

    /**
     * Elimina una reserva a MongoDB pel seu ID.
     *
     * @param id l'ID de la reserva a eliminar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
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
