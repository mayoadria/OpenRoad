package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.security.UserUtils;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class DashboardClient {

    @Autowired
    private ReservaServiceSQL reservaServiceSQL;

    @GetMapping("/reserves")
    public String reserves(Model model) {

        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = new Client();
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari; // Usuario autenticado como Cliente
        }

        List<Reserva> reserva = reservaServiceSQL.buscarReservaPerDNI(client.getDni());

        model.addAttribute("reserva", reserva);
        return "DashboardClient";
    }

    @GetMapping("/edit/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, Model model) {
        Reserva usuario = reservaServiceSQL.llistarReservaPerId(idReserva);
        if (usuario == null) {

            return "redirect:/client/reserves"; // Redirigir si no existe el usuario
        }
        model.addAttribute("reserva", usuario);
        return "ModificarReserva"; // Cargar la vista para editar
    }

    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Reserva reserva, @RequestParam Long idReserva, Model model) {
        // Buscar la reserva existente por ID
        Reserva clienteExistente = reservaServiceSQL.llistarReservaPerId(idReserva);
        if (clienteExistente == null) {
            return "DashboardClient"; // Redirige a la página con un mensaje de error
        }

        // Actualizar los datos de la reserva
        clienteExistente.setFechaEntrega(reserva.getFechaEntrega());
        clienteExistente.setFechaRecogida(reserva.getFechaRecogida());
        clienteExistente.setPreuComplert(reserva.getPreuComplert());

        // Guardar los cambios
        reservaServiceSQL.modificarReserva(clienteExistente);

        // Agregar el objeto actualizado al modelo para confirmación

        return "redirect:/client/reserves"; // Redirige a la vista de éxito o confirmación

    }

    @GetMapping("/delete/{idReserva}")
    public String deleteClient(@PathVariable Long idReserva) {
        reservaServiceSQL.eliminarReservaPerId(idReserva);
        return "redirect:/client/reserves";
    }


}
