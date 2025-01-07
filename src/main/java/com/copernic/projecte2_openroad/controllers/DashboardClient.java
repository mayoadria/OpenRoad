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

/**
 * Controlador per gestionar el tauler de control dels clients, incloent la gestió de reserves.
 */
@Controller
@RequestMapping("/client")
public class DashboardClient {

    @Autowired
    private ReservaServiceSQL reservaServiceSQL;

    /**
     * Mostra la llista de reserves associades al client actual.
     *
     * @param model l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return el nom de la vista del tauler de control del client.
     */
    @GetMapping("/reserves")
    public String reserves(Model model) {
        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = new Client();
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari;
        }

        List<Reserva> reserva = reservaServiceSQL.buscarReservaPerDNI(client.getDni());

        model.addAttribute("reserva", reserva);
        return "DashboardClient";
    }

    /**
     * Mostra el formulari per editar una reserva específica.
     *
     * @param idReserva l'identificador de la reserva a editar.
     * @param model     l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return el nom de la vista per editar la reserva.
     */
    @GetMapping("/edit/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, Model model) {
        Reserva usuario = reservaServiceSQL.llistarReservaPerId(idReserva);
        if (usuario == null) {
            return "redirect:/client/reserves"; // Redirigir si la reserva no existeix
        }
        model.addAttribute("reserva", usuario);
        return "ModificarReserva";
    }

    /**
     * Guarda els canvis fets a una reserva.
     *
     * @param reserva   l'objecte {@link Reserva} amb els nous valors de la reserva.
     * @param idReserva l'identificador de la reserva a modificar.
     * @param model     l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return una redirecció a la vista amb la llista de reserves.
     */
    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Reserva reserva, @RequestParam Long idReserva, Model model) {
        Reserva clienteExistente = reservaServiceSQL.llistarReservaPerId(idReserva);
        if (clienteExistente == null) {
            return "DashboardClient"; // Redirigeix si la reserva no existeix
        }

        clienteExistente.setFechaEntrega(reserva.getFechaEntrega());
        clienteExistente.setFechaRecogida(reserva.getFechaRecogida());
        clienteExistente.setPreuComplert(reserva.getPreuComplert());

        reservaServiceSQL.modificarReserva(clienteExistente);

        return "redirect:/client/reserves";
    }

    /**
     * Elimina una reserva específica.
     *
     * @param idReserva l'identificador de la reserva a eliminar.
     * @return una redirecció a la vista amb la llista de reserves.
     */
    @GetMapping("/delete/{idReserva}")
    public String deleteClient(@PathVariable Long idReserva) {
        reservaServiceSQL.eliminarReservaPerId(idReserva);
        return "redirect:/client/reserves";
    }
}
