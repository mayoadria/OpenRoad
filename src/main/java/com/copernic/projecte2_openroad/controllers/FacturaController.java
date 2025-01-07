package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controlador per gestionar la generació de factures per a les reserves.
 */
@Controller
public class FacturaController {

    @Autowired
    private ReservaServiceSQL reservaServiceSQL;

    /**
     * Mètode per mostrar la factura d'una reserva específica.
     *
     * @param idReserva l'identificador únic de la reserva.
     * @param model     l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return el nom de la vista "factura".
     */
    @GetMapping("/factura/{idReserva}")
    public String factura(@PathVariable("idReserva") Long idReserva, Model model) {

        // Obté la reserva pel seu identificador
        Reserva reserva = reservaServiceSQL.llistarReservaPerId(idReserva);

        // Obté el vehicle associat a la reserva
        Vehicle vehicle = reserva.getVehicle();

        // Obté el client associat a la reserva
        Client client = reserva.getClient();

        // Afegeix les dades de la reserva, el vehicle i el client al model
        model.addAttribute("reserva", reserva);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("client", client);

        // Retorna el nom de la vista per mostrar la factura
        return "factura";
    }
}
