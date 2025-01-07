package com.copernic.projecte2_openroad.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;
import com.copernic.projecte2_openroad.model.mysql.*;
import com.copernic.projecte2_openroad.security.UserUtils;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.copernic.projecte2_openroad.model.enums.EstatVehicle;
import com.copernic.projecte2_openroad.service.mongodb.IncidenciaServiceMongo;
import com.copernic.projecte2_openroad.service.mongodb.ReservaServiceMongo;
import com.copernic.projecte2_openroad.service.mysql.IncidenciaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador per al panell de l'agent.
 * Gestiona la visualització, creació, modificació i eliminació de vehicles, reserves i incidències.
 */
@Controller
@RequestMapping("/agent")
public class AgentDashboardController {

    // Serveis de MySQL
    @Autowired
    VehicleServiceSQL vehicleServiceSQL;

    @Autowired
    ReservaServiceSQL reservaServiceSQL;

    @Autowired
    IncidenciaServiceSQL incidenciaServiceSQL;

    // Serveis de MongoDB
    @Autowired
    ReservaServiceMongo reservaServiceMongo;

    @Autowired
    IncidenciaServiceMongo incidenciaServiceMongo;

    /**
     * Mètode que mostra el panell d'agent amb la informació dels vehicles, reserves i incidències.
     * Permet filtrar vehicles per matrícula, estat, marca i model.
     *
     * @param matriculaFilt Filtres de matrícula dels vehicles.
     * @param estatsFilt Filtres d'estat dels vehicles.
     * @param marquesFilt Filtres de marca dels vehicles.
     * @param modelsFilt Filtres de model dels vehicles.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Vista del panell de l'agent.
     */
    @GetMapping("/dashboard")
    public String dashboardAgent(
            @RequestParam(name = "matricula", required = false) String matriculaFilt,
            @RequestParam(name = "estats", required = false) String estatsFilt,
            @RequestParam(name = "marques", required = false) String marquesFilt,
            @RequestParam(name = "models", required = false) String modelsFilt,
            Model model) {

        // Obtener los datos del agente actual
        Object agentObj = UserUtils.obtenirDadesUsuariModel(model);
        if (!(agentObj instanceof Agent)) {
            model.addAttribute("error", "No s'han pogut obtenir les dades de l'agent.");
            return "errorPage"; // Canviar a una pàgina d'error adequada
        }
        Agent agent = (Agent) agentObj;

        // Obtener vehículos en la misma localidad que el agente
        List<Vehicle> vehicles = new ArrayList<>();
        Boolean haveLocal = false;
        if (agent.getLocalitat() != null) {
            vehicles = vehicleServiceSQL.getVehiclesByAgentLocalitat(agent.getLocalitat().getCodiPostalLoc());
            haveLocal = true;
        }
        List<Reserva> reserves = reservaServiceSQL.llistarReserves();
        List<Incidencia> incidencies = incidenciaServiceSQL.llistarIncidencies();

        List<EstatVehicle> estatsVehicle = Arrays.asList(EstatVehicle.values());
        Collections.sort(estatsVehicle);

        // Obtener marcas y modelos
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());

        List<String> models = vehicleServiceSQL.getAtributsVehicle(Vehicle::getModel, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());

        // Aplicar els filtres
        if (matriculaFilt != null && !matriculaFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getMatricula().equalsIgnoreCase(matriculaFilt))
                    .collect(Collectors.toList());
        }

        if (estatsFilt != null && !estatsFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getEstatVehicle() == EstatVehicle.valueOf(estatsFilt))
                    .collect(Collectors.toList());
        }

        if (marquesFilt != null && !marquesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getMarca().equalsIgnoreCase(marquesFilt))
                    .collect(Collectors.toList());
        }

        if (modelsFilt != null && !modelsFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getModel().equalsIgnoreCase(modelsFilt))
                    .collect(Collectors.toList());
        }

        // Actualizar los datos en el modelo
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("reserves", reserves);
        model.addAttribute("incidencies", incidencies);
        model.addAttribute("estatsVehicle", estatsVehicle);
        model.addAttribute("marques", marques);
        model.addAttribute("models", models);
        model.addAttribute("haveLocal", haveLocal);
        return "dashboardAgent";
    }


    /**
     * Mètode que mostra els detalls d'un vehicle específic.
     *
     * @param matricula Matricula del vehicle a mostrar.
     * @param visualizar Valor que indica si el vehicle es visualitzarà o es podrà editar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Vista per modificar un vehicle.
     */
    @GetMapping("/vehicle/{matricula}")
    public String detallsVehicle(@PathVariable("matricula") String matricula, boolean visualizar, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).orElse(null);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        model.addAttribute("visualizar", true);
        return "ModificarVehicles";
    }

    /**
     * Mètode que mostra el formulari per crear un nou vehicle.
     *
     * @param model Model de l'objecte per enviar a la vista.
     * @return Vista per crear un vehicle.
     */
    @GetMapping("/crear_vehicle")
    public String mostrarFormulariVehicle(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);

        return "crearVehicle";
    }

    /**
     * Mètode per crear un vehicle nou a la base de dades.
     *
     * @param vehicle Objeto Vehicle amb les dades del vehicle a crear.
     * @param result Resultat de la validació del formulari.
     * @param file Fitxer de la imatge del vehicle.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Vista de la pàgina següent després de la creació.
     * @throws IOException Si hi ha un error en guardar la imatge.
     */
    @PostMapping("/crear")
    public String crearVehicle(@Valid @ModelAttribute Vehicle vehicle, BindingResult result,
                               @RequestParam("imagen") MultipartFile file, Model model) {
        try {
            // Crear i guardar la imatge
            Imagen image = new Imagen();
            image.setNombre(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setImageData(file.getBytes());

            // Associar la imatge amb el vehicle
            vehicle.setImage(image);

            // Generar la URL de la imatge
            String base64Image = Base64.getEncoder().encodeToString(image.getImageData());
            String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
            vehicle.setImageUrl(imageUrl);

            // Obtenir la localitat de l'agent
            Object agentObj = UserUtils.obtenirDadesUsuariModel(model);
            if (agentObj instanceof Agent) {
                Agent agent = (Agent) agentObj;
                if (agent.getLocalitat() == null) {
                    return "redirect:/agent/dashboard";
                } else {
                    vehicle.setLocalitat(agent.getLocalitat());
                }
            }
            vehicle.setEstatVehicle(EstatVehicle.INACTIU);
            if (result.hasErrors()) {
                return "crearVehicle";
            } else {
                vehicleServiceSQL.guardarVehicle(vehicle);
                return "redirect:/agent/dashboard";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Mètode per eliminar un vehicle de la base de dades.
     *
     * @param matricula Matricula del vehicle que es vol eliminar.
     * @return Redirecció a la pàgina principal del panell de l'agent.
     */
    @GetMapping("/delete/vehicle/{matricula}")
    public String deleteClient(@PathVariable String matricula) {
        vehicleServiceSQL.eliminarVehiclePerId(matricula);
        return "redirect:/agent/dashboard";
    }

    /**
     * Mètode per editar un vehicle existent.
     *
     * @param matricula Matricula del vehicle que es vol editar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Vista per modificar un vehicle.
     */
    @GetMapping("/edit/vehicle/{matricula}")
    public String editVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            model.addAttribute("visualizar", false);
        }
        return "ModificarVehicles"; // Nom del fitxer Thymeleaf
    }

    /**
     * Mètode per activar un vehicle.
     *
     * @param matricula Matricula del vehicle a activar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Redirecció al panell de l'agent.
     */
    @GetMapping("/activar/vehicle/{matricula}")
    public String activarVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            vehicle.get().setEstatVehicle(EstatVehicle.ACTIU);
            vehicleServiceSQL.modificarVehicle(vehicle.get());
        }
        return "redirect:/agent/dashboard";
    }

    /**
     * Mètode per desactivar un vehicle.
     *
     * @param matricula Matricula del vehicle a desactivar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Redirecció al panell de l'agent.
     */
    @GetMapping("/desactivar/vehicle/{matricula}")
    public String desactivarVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            vehicle.get().setEstatVehicle(EstatVehicle.INACTIU);
            vehicleServiceSQL.modificarVehicle(vehicle.get());
        }
        return "redirect:/agent/dashboard";
    }

    /**
     * Mètode per lliurar un vehicle.
     *
     * @param matricula Matricula del vehicle a lliurar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Redirecció al panell de l'agent.
     */
    @GetMapping("/lliurar/vehicle/{matricula}")
    public String lliurarVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            vehicle.get().setEstatVehicle(EstatVehicle.ENTREGAT);
            vehicleServiceSQL.modificarVehicle(vehicle.get());
        }
        return "redirect:/agent/dashboard";
    }

    /**
     * Mètode per guardar els canvis d'un vehicle després de la seva edició.
     *
     * @param vehiculo Vehicle amb les dades actualitzades.
     * @param matricula Matrícula del vehicle que es vol actualitzar.
     * @param model Model de l'objecte per enviar a la vista.
     * @return Redirecció al panell de l'agent.
     */
    @PostMapping("/editVehicle")
    public String guardarCambios(@ModelAttribute Vehicle vehiculo, @RequestParam String matricula, Model model) {
        // Buscar el vehicle que s'està editant per la seva matrícula enviada al formulari
        Optional<Vehicle> vehiculoExistente = vehicleServiceSQL.findByMatricula(matricula);

        if (vehiculoExistente.isPresent()) {
            Vehicle vehiculoACambiar = vehiculoExistente.get();

            // Actualitzar les dades del vehicle
            vehiculoACambiar.setMarca(vehiculo.getMarca());
            vehiculoACambiar.setModel(vehiculo.getModel());
            vehiculoACambiar.setPreuDia(vehiculo.getPreuDia());
            vehiculoACambiar.setFianca(vehiculo.getFianca());
            vehiculoACambiar.setDiesLloguerMinim(vehiculo.getDiesLloguerMinim());
            vehiculoACambiar.setDiesLloguerMaxim(vehiculo.getDiesLloguerMaxim());
            vehiculoACambiar.setPlaces(vehiculo.getPlaces());
            vehiculoACambiar.setPortes(vehiculo.getPortes());
            vehiculoACambiar.setCaixaCanvis(vehiculo.getCaixaCanvis());
            vehiculoACambiar.setMarxes(vehiculo.getMarxes());
            vehiculoACambiar.setCombustible(vehiculo.getCombustible());
            vehiculoACambiar.setColor(vehiculo.getColor());
            vehiculoACambiar.setEstatVehicle(vehiculo.getEstatVehicle());
            vehiculoACambiar.setAnyVehicle(vehiculo.getAnyVehicle());
            vehiculoACambiar.setKm(vehiculo.getKm());

            // Guardar els canvis
            vehicleServiceSQL.modificarVehicle(vehiculoACambiar);
            return "redirect:/agent/dashboard"; // Redirigir al panell d'administració
        } else {
            model.addAttribute("error", "El vehicle no existeix o no és vàlid.");
            return "ModificarVehicles"; // Mostrar la pàgina amb l'error
        }
    }

    /**
     * Mètode per activar una reserva.
     *
     * @param idReserva Identificador de la reserva a activar.
     * @return Redirecció al panell de l'agent.
     */
    @GetMapping("/activateUser/{idReserva}")
    public String activateUser(@PathVariable Long idReserva) {
        reservaServiceSQL.activarReserva(idReserva);
        return "redirect:/agent/dashboard";
    }

    @Controller
    @RequestMapping("/agent")
    public class IncidenciaController {

        @GetMapping("/crear_incidencia")
        public String mostrarFormularioCrearIncidencia(Model model) {
            model.addAttribute("incidencia", new Incidencia());

            // Añadir los vehículos disponibles
            Object agentObj = UserUtils.obtenirDadesUsuariModel(model);
            if (agentObj instanceof Agent) {
                Agent agent = (Agent) agentObj;
                List<Vehicle> vehicles = vehicleServiceSQL.getVehiclesByAgentLocalitat(agent.getLocalitat().getCodiPostalLoc());
                model.addAttribute("vehicles", vehicles);
            } else {
                model.addAttribute("error", "No se pudo obtener el agente.");
                return "errorPage"; // Página de error
            }

            return "Incidencia"; // Nombre del archivo Thymeleaf (Incidencia.html)
        }

        @PostMapping("/crear_incidencia")
        public String guardarIncidencia(@ModelAttribute Incidencia incidencia, Model model) {
            if (incidencia.getVehicle() == null || incidencia.getVehicle().getMatricula() == null || incidencia.getVehicle().getMatricula().isEmpty()) {
                model.addAttribute("error", "Debe seleccionar un vehículo.");
                return "Incidencia";
            }

            Optional<Vehicle> optionalVehicle = vehicleServiceSQL.findByMatricula(incidencia.getVehicle().getMatricula());
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                incidencia.setVehicle(vehicle); // Asociar el vehículo a la incidencia
                incidenciaServiceSQL.guardarIncidencia(incidencia); // Guardar incidencia
                return "redirect:/agent/dashboard";
            } else {
                model.addAttribute("error", "No se encontró el vehículo especificado.");
                return "Incidencia";
            }
        }
    }


    @GetMapping("/incidencia/editar/{id}")
    public String editarIncidencia(@PathVariable("id") Long id, Model model) {
        try {
            // Obtener la incidencia usando el servicio
            Incidencia incidencia = incidenciaServiceSQL.llistarIncidenciaPerId(id);

            // Añadir la incidencia al modelo
            model.addAttribute("incidencia", incidencia);

            // Obtener los vehículos asociados al agente para el formulario
            Object agentObj = UserUtils.obtenirDadesUsuariModel(model);
            if (agentObj instanceof Agent) {
                Agent agent = (Agent) agentObj;
                List<Vehicle> vehicles = vehicleServiceSQL.getVehiclesByAgentLocalitat(agent.getLocalitat().getCodiPostalLoc());
                model.addAttribute("vehicles", vehicles);
            }

            return "EditarIncidencia"; // Vista del formulario de edición
        } catch (Exception e) {
            // Manejar la excepción si la incidencia no se encuentra
            model.addAttribute("error", "No se encontró la incidencia con ID: " + id);
            return "redirect:/agent/dashboard"; // Redirige al dashboard si no se encuentra la incidencia
        }
    }

    @PostMapping("/guardar_incidencia")
    public String guardarIncidencia(@ModelAttribute Incidencia incidencia, Model model) {
        try {
            // Actualiza la incidencia existente
            Incidencia incidenciaExistente = incidenciaServiceSQL.llistarIncidenciaPerId(incidencia.getIdIncidencia());
            incidenciaExistente.setTitol(incidencia.getTitol());
            incidenciaExistente.setCost(incidencia.getCost());
            incidenciaExistente.setDataInici(incidencia.getDataInici());
            incidenciaExistente.setDataFinal(incidencia.getDataFinal());
            incidenciaExistente.setVehicle(incidencia.getVehicle());
            incidenciaExistente.setEstatIncidencia(incidencia.getEstatIncidencia());

            // Guarda la incidencia actualizada
            incidenciaServiceSQL.guardarIncidencia(incidenciaExistente);

            // Redirige al dashboard después de guardar
            return "redirect:/agent/dashboard";
        } catch (Exception e) {
            // Maneja errores y vuelve a la página de edición
            model.addAttribute("error", "Error al guardar la incidencia: " + e.getMessage());
            model.addAttribute("incidencia", incidencia);
            return "EditarIncidencia"; // Asegúrate de que este es el nombre correcto de tu archivo HTML
        }
    }

    /**
     * Elimina una incidencia de la base de datos según el ID proporcionado.
     * Si la eliminación es exitosa, redirige al dashboard del agente.
     * Si ocurre algún error, muestra un mensaje de error en el dashboard.
     *
     * @param id El identificador de la incidencia a eliminar.
     * @param model El modelo para agregar atributos de error en caso de fallo.
     * @return Redirección al dashboard del agente o a la vista de error en caso de fallo.
     */
    @GetMapping("/incidencia/eliminar/{id}")
    public String eliminarIncidencia(@PathVariable("id") Long id, Model model) {
        try {
            // Llama al servicio para eliminar la incidencia
            String mensaje = incidenciaServiceSQL.eliminarIncidenciaPerId(id);

            // Imprime el mensaje de éxito o error en los logs (opcional para depuración)
            System.out.println(mensaje);

            // Redirige al dashboard después de la eliminación
            return "redirect:/agent/dashboard";
        } catch (Exception e) {
            // Maneja errores en caso de que no se pueda eliminar
            model.addAttribute("error", "No se pudo eliminar la incidencia: " + e.getMessage());
            return "dashboardAgent"; // Vuelve al dashboard mostrando un mensaje de error
        }
    }

    /**
     * Activa una incidencia cambiando su estado a "OBERTA" (abierta).
     * Si la operación es exitosa, redirige al dashboard del agente.
     * Si ocurre un error, muestra un mensaje de error en el dashboard.
     *
     * @param id El identificador de la incidencia a activar.
     * @param model El modelo para agregar atributos de error en caso de fallo.
     * @return Redirección al dashboard del agente o a la vista de error en caso de fallo.
     */
    @GetMapping("/incidencia/activar/{id}")
    public String activarIncidencia(@PathVariable("id") Long id, Model model) {
        try {
            // Busca la incidencia por ID y activa
            Incidencia incidencia = incidenciaServiceSQL.llistarIncidenciaPerId(id);
            incidencia.setEstatIncidencia(EstatIncidencia.OBERTA); // Cambia el estado a "ACTIVA"
            incidenciaServiceSQL.guardarIncidencia(incidencia);
            return "redirect:/agent/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo activar la incidencia: " + e.getMessage());
            return "dashboardAgent";
        }
    }

    /**
     * Desactiva una incidencia cambiando su estado a "TANCADA" (cerrada).
     * Si la operación es exitosa, redirige al dashboard del agente.
     * Si ocurre un error, muestra un mensaje de error en el dashboard.
     *
     * @param id El identificador de la incidencia a desactivar.
     * @param model El modelo para agregar atributos de error en caso de fallo.
     * @return Redirección al dashboard del agente o a la vista de error en caso de fallo.
     */
    @GetMapping("/incidencia/desactivar/{id}")
    public String desactivarIncidencia(@PathVariable("id") Long id, Model model) {
        try {
            // Busca la incidencia por ID y desactiva
            Incidencia incidencia = incidenciaServiceSQL.llistarIncidenciaPerId(id);
            incidencia.setEstatIncidencia(EstatIncidencia.TANCADA); // Cambia el estado a "INACTIVA"
            incidenciaServiceSQL.guardarIncidencia(incidencia);
            return "redirect:/agent/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo desactivar la incidencia: " + e.getMessage());
            return "dashboardAgent";
        }
    }






}
