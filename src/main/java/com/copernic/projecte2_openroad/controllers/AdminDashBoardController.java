package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.RemoteServer;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

    @Autowired
    UsuariServiceSQL usuariServiceSQL;
    @Autowired
    ReservaServiceSQL reservaServiceSQL;


    @GetMapping("/dashboard")
    public String dashboard(
        @RequestParam(name = "dniClient", required = false) String dniClientFilt,
        @RequestParam(name = "emailClient", required = false) String emailClientFilt,
        @RequestParam(name = "paisClient", required = false) String paisClientFilt,
        Model model) {

        List<Agent> agents = usuariServiceSQL.llistarAgents();
        List<Client> clients = usuariServiceSQL.llistarClient();
        List<Reserva> reserva = reservaServiceSQL.llistarReserves();

        List<Pais> paisos = clients.stream().map(Client::getPais).distinct().toList();

       if (dniClientFilt != null && !dniClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getDni().equalsIgnoreCase(dniClientFilt))
                    .collect(Collectors.toList());
        }

        if (emailClientFilt != null && !emailClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getEmail().equalsIgnoreCase(emailClientFilt))
                    .collect(Collectors.toList());
        }

        if (paisClientFilt != null && !paisClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getPais() == Pais.valueOf(paisClientFilt))
                    .collect(Collectors.toList());
        }

        model.addAttribute("agents", agents);
        model.addAttribute("clients", clients);
        model.addAttribute("paisos", paisos);
        model.addAttribute("reservas", reserva);
        return "dashboard"; // dashboard.html en templates.
    }

    @GetMapping("/crearAgente")
    public String crearAgent(){
        return "CrearAgente";
    }

    @GetMapping("/delete/agent/{nomUsuari}")
    public String deleteAgent(@PathVariable String nomUsuari){
        usuariServiceSQL.eliminarAgentPerNomUsuari(nomUsuari);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/client/{nomUsuari}")
    public String deleteClient(@PathVariable String nomUsuari){
        usuariServiceSQL.eliminarClientPerNomUsuari(nomUsuari);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{nomUsuari}")
    public String editarUsuario(@PathVariable String nomUsuari, Model model) {
        Usuari usuario = usuariServiceSQL.findByNomUsuari(nomUsuari);
        if (usuario == null) {
            return "redirect:/admin/dashboard";  // Redirigir si no existe el usuario
        }
        model.addAttribute("cliente", usuario);
        return "EditarOtrosPerfilesAdmin";  // Cargar la vista para editar
    }

    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Client cliente, @RequestParam String nomUsuari, Model model) {
        // Buscar el usuario que se está editando por su nomUsuari enviado en el formulario
        Usuari clienteExistente = usuariServiceSQL.findByNomUsuari(nomUsuari);

        if (clienteExistente != null && clienteExistente instanceof Client) {
            Client clienteACambiar = (Client) clienteExistente;

            // Actualizar los datos del cliente
            clienteACambiar.setNom(cliente.getNom());
            clienteACambiar.setCognom1(cliente.getCognom1());
            clienteACambiar.setCognom2(cliente.getCognom2());
            clienteACambiar.setNumContacte1(cliente.getNumContacte1());
            clienteACambiar.setCodiPostal(cliente.getCodiPostal());
            clienteACambiar.setAdreca(cliente.getAdreca());
            clienteACambiar.setEmail(cliente.getEmail());

            // Guardar los cambios
            usuariServiceSQL.modificarClient(clienteACambiar);
            return "redirect:/admin/dashboard";  // Redirigir al panel de administración
        } else {
            model.addAttribute("error", "El cliente no existe o no es válido.");
            return "EditarOtrosPerfilesAdmin";  // Mostrar la página con el error
        }
    }
    @GetMapping("/activateUser/{nomUsuari}")
    public String activateUser(@PathVariable String nomUsuari) {
        usuariServiceSQL.activateUser(nomUsuari);
        return "redirect:/admin/dashboard";
    }

}

