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

@Controller
public class FacturaController {

    @Autowired
    private ReservaServiceSQL reservaServiceSQL;
    @GetMapping("/factura/{idReserva}")
    public String factura(@PathVariable("idReserva") Long idReserva, Model model) {

        Reserva reserva = reservaServiceSQL.llistarReservaPerId(idReserva);
        Vehicle vehicle = reserva.getVehicle();
        Client client = reserva.getClient();
        model.addAttribute("reserva", reserva);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("client", client);

        return "factura";
    }


}
