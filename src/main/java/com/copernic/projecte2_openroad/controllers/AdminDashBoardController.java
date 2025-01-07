package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mysql.*;
import com.copernic.projecte2_openroad.service.mysql.LocalitatServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador que maneja el panel de administración del sistema, donde los administradores pueden gestionar
 * agentes, clientes, reservas, localizaciones, y realizar varias acciones de edición y eliminación.
 */
@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

    @Autowired
    UsuariServiceSQL usuariServiceSQL;
    @Autowired
    ReservaServiceSQL reservaServiceSQL;
    @Autowired
    LocalitatServiceSQL localitatServiceSQL;

    /**
     * Muestra el panel de administración con filtros para gestionar usuarios y mostrar reservas y localizaciones.
     *
     * @param dniClientFilt Filtro por DNI del cliente
     * @param dniAgentFilt Filtro por DNI del agente
     * @param emailClientFilt Filtro por email del cliente
     * @param emailAgentFilt Filtro por email del agente
     * @param paisClientFilt Filtro por país del cliente
     * @param paisAgentFilt Filtro por país del agente
     * @param CognomClientFilt Filtro por primer apellido del cliente
     * @param TelefonClientFilt Filtro por número de contacto del cliente
     * @param model Modelo para pasar los datos a la vista
     * @return La vista del dashboard con los datos filtrados
     */
    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(name = "dniClient", required = false) String dniClientFilt,
            @RequestParam(name = "dniAgent", required = false) String dniAgentFilt,
            @RequestParam(name = "emailClient", required = false) String emailClientFilt,
            @RequestParam(name = "emailAgent", required = false) String emailAgentFilt,
            @RequestParam(name = "paisClient", required = false) String paisClientFilt,
            @RequestParam(name = "paisAgent", required = false) String paisAgentFilt,
            @RequestParam(name = "cognom1", required = false) String CognomClientFilt,
            @RequestParam(name = "numContacte", required = false) String TelefonClientFilt,
            Model model) {

        List<Agent> agents = usuariServiceSQL.llistarAgents().stream()
                .filter(agent -> agent instanceof Agent && !(agent instanceof Admin))
                .collect(Collectors.toList());
        List<Client> clients = usuariServiceSQL.llistarClient().stream()
                .filter(client -> client instanceof Client && !(client instanceof Agent) && !(client instanceof Admin))
                .collect(Collectors.toList());
        List<Reserva> reserva = reservaServiceSQL.llistarReserves();
        List<Localitat> localitat = localitatServiceSQL.llistarLocalitats();

        List<Pais> paisosClient = clients.stream().map(Client::getPais).distinct().toList();
        List<Pais> paisosAgent = agents.stream().map(Agent::getPais).distinct().toList();

        if (dniClientFilt != null && !dniClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getDni().contains(dniClientFilt))
                    .collect(Collectors.toList());
        }

        if (dniAgentFilt != null && !dniAgentFilt.isEmpty()) {
            agents = agents.stream()
                    .filter(a -> a.getDni().contains(dniAgentFilt))
                    .collect(Collectors.toList());
        }

        if (emailClientFilt != null && !emailClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getEmail().contains(emailClientFilt))
                    .collect(Collectors.toList());
        }

        if (emailAgentFilt != null && !emailAgentFilt.isEmpty()) {
            agents = agents.stream()
                    .filter(a -> a.getEmail().contains(emailAgentFilt))
                    .collect(Collectors.toList());
        }

        if (paisClientFilt != null && !paisClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getPais() == Pais.valueOf(paisClientFilt))
                    .collect(Collectors.toList());
        }
        if (paisAgentFilt != null && !paisAgentFilt.isEmpty()) {
            agents = agents.stream()
                    .filter(a -> a.getPais() == Pais.valueOf(paisAgentFilt))
                    .collect(Collectors.toList());
        }
        if (TelefonClientFilt != null && !TelefonClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getNumContacte1().contains(TelefonClientFilt))
                    .collect(Collectors.toList());
        }
        if (CognomClientFilt != null && !CognomClientFilt.isEmpty()) {
            clients = clients.stream()
                    .filter(c -> c.getCognom1().contains(CognomClientFilt))
                    .collect(Collectors.toList());
        }

        model.addAttribute("agents", agents);
        model.addAttribute("clients", clients);
        model.addAttribute("paisosClient", paisosClient);
        model.addAttribute("paisosAgent", paisosAgent);
        model.addAttribute("reservas", reserva);
        model.addAttribute("localitats", localitat);
        return "dashboard"; // dashboard.html en templates.
    }

    /**
     * Redirige a la vista para crear un nuevo agente.
     *
     * @return La vista para crear un nuevo agente
     */
    @GetMapping("/crearAgente")
    public String crearAgent() {
        return "CrearAgente";
    }

    /**
     * Permite editar la información de un agente específico.
     *
     * @param nomUsuari Nombre de usuario del agente
     * @param model Modelo para pasar los datos a la vista
     * @return La vista para editar los datos del agente
     */
    @GetMapping("/editarAgent/{nomUsuari}")
    public String editarAgent(@PathVariable String nomUsuari, Model model) {
        Agent agent = (Agent) usuariServiceSQL.findByNomUsuari(nomUsuari);
        if (agent instanceof Agent) {
            agent.setLocalitat(new Localitat());
            model.addAttribute("paisos", Pais.values());
            model.addAttribute("localitats", localitatServiceSQL.llistarLocalitats());
            model.addAttribute("agent", agent);
            model.addAttribute("dniAgent", agent.getDni());
        }
        return "editarAgent";
    }

    /**
     * Elimina un agente por su nombre de usuario.
     *
     * @param nomUsuari Nombre de usuario del agente a eliminar
     * @return Redirige al panel de administración
     */
    @GetMapping("/delete/agent/{nomUsuari}")
    public String deleteAgent(@PathVariable String nomUsuari) {
        usuariServiceSQL.eliminarAgentPerNomUsuari(nomUsuari);
        return "redirect:/admin/dashboard";
    }

    /**
     * Elimina un cliente por su nombre de usuario.
     *
     * @param nomUsuari Nombre de usuario del cliente a eliminar
     * @return Redirige al panel de administración
     */
    @GetMapping("/delete/client/{nomUsuari}")
    public String deleteClient(@PathVariable String nomUsuari) {
        usuariServiceSQL.eliminarClientPerNomUsuari(nomUsuari);
        return "redirect:/admin/dashboard";
    }

    /**
     * Permite editar los datos de un usuario específico.
     *
     * @param nomUsuari Nombre de usuario del cliente o agente
     * @param visualizar Flag que indica si solo se visualizarán los datos o se editarán
     * @param model Modelo para pasar los datos a la vista
     * @return La vista para editar los datos del usuario
     */
    @GetMapping("/edit/{nomUsuari}")
    public String editarUsuario(@PathVariable String nomUsuari, boolean visualizar, Model model) {
        Usuari usuario = usuariServiceSQL.findByNomUsuari(nomUsuari);
        if (usuario == null) {
            return "redirect:/admin/dashboard"; // Redirigir si no existe el usuario
        }
        model.addAttribute("cliente", usuario);
        model.addAttribute("visualizar", visualizar);
        return "EditarOtrosPerfilesAdmin"; // Cargar la vista para editar
    }

    /**
     * Guarda los cambios realizados en los datos de un cliente.
     *
     * @param cliente Cliente con los datos actualizados
     * @param nomUsuari Nombre de usuario del cliente
     * @param model Modelo para pasar los datos a la vista
     * @return Redirige al panel de administración después de guardar los cambios
     */
    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Client cliente, @RequestParam String nomUsuari, Model model) {
        Usuari clienteExistente = usuariServiceSQL.findByNomUsuari(nomUsuari);

        if (clienteExistente != null && clienteExistente instanceof Client) {
            Client clienteACambiar = (Client) clienteExistente;

            // Actualizar los datos del cliente
            clienteACambiar.setNom(cliente.getNom());
            clienteACambiar.setCognom1(cliente.getCognom1());
            clienteACambiar.setNumContacte1(cliente.getNumContacte1());
            clienteACambiar.setCodiPostal(cliente.getCodiPostal());
            clienteACambiar.setAdreca(cliente.getAdreca());
            clienteACambiar.setEmail(cliente.getEmail());

            // Guardar los cambios
            usuariServiceSQL.modificarClient(clienteACambiar);
            return "redirect:/admin/dashboard"; // Redirigir al panel de administración
        } else {
            model.addAttribute("error", "El cliente no existe o no es válido.");
            return "EditarOtrosPerfilesAdmin"; // Mostrar la página con el error
        }
    }

    /**
     * Activa un usuario en el sistema.
     *
     * @param nomUsuari Nombre de usuario del usuario a activar
     * @return Redirige al panel de administración
     */
    @GetMapping("/activateUser/{nomUsuari}")
    public String activateUser(@PathVariable String nomUsuari) {
        usuariServiceSQL.activateUser(nomUsuari);
        return "redirect:/admin/dashboard";
    }

    /**
     * Muestra el formulario para crear una nueva localización.
     *
     * @param model Modelo para pasar los datos a la vista
     * @return La vista para crear una nueva localización
     */
    @GetMapping("/crear_localitzacio")
    public String mostrarFormulariLocalitzacio(Model model) {
        Localitat localitat = new Localitat();
        model.addAttribute("localitat", localitat);

        return "crearLocalitat";
    }

    /**
     * Crea una nueva localización.
     *
     * @param localitat Objeto Localitat con los datos a guardar
     * @return Redirige al panel de administración después de guardar la localización
     */
    @PostMapping("/crearL")
    public String crearLocalitat(@ModelAttribute Localitat localitat) {
        localitatServiceSQL.guardarLocalitat(localitat);
        return "redirect:/admin/dashboard";
    }

    /**
     * Elimina una localización por su código postal.
     *
     * @param codiPostalLoc Código postal de la localización a eliminar
     * @return Redirige al panel de administración después de eliminar la localización
     */
    @GetMapping("/delete/{codiPostalLoc}")
    public String deletelocalitat(@PathVariable String codiPostalLoc) {
        localitatServiceSQL.eliminarLocalitatPerId(codiPostalLoc);
        return "redirect:/admin/dashboard";
    }

    /**
     * Muestra el formulario para modificar una localización existente.
     *
     * @param codiPostalLoc Código postal de la localización a modificar
     * @param model Modelo para pasar los datos a la vista
     * @return La vista para modificar la localización
     */
    @GetMapping("/modificar-localitat/{codiPostalLoc}")
    public String modificarLocalitatForm(@PathVariable("codiPostalLoc") String codiPostalLoc, Model model) {
        Localitat localitat = localitatServiceSQL.findByNomUsuari(codiPostalLoc);

        model.addAttribute("localitat", localitat);

        return "modificarLocalitat"; // Nombre de la plantilla Thymeleaf
    }

    /**
     * Modifica una localización existente.
     *
     * @param localitat Objeto Localitat con los datos actualizados
     * @return Redirige al panel de administración después de modificar la localización
     */
    @PostMapping("/modificarL")
    public String modificarLocalitat(@ModelAttribute Localitat localitat) {
        localitatServiceSQL.modificarLocalitat(localitat); // Aquí llamas al servicio para actualizar la localización
        return "redirect:/admin/dashboard"; // Redirige después de modificar
    }

}
