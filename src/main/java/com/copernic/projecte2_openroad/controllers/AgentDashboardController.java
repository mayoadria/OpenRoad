package com.copernic.projecte2_openroad.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.copernic.projecte2_openroad.model.enums.EstatIncidencia;
import com.copernic.projecte2_openroad.model.mysql.*;
import com.copernic.projecte2_openroad.security.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.copernic.projecte2_openroad.model.enums.EstatVehicle;
import com.copernic.projecte2_openroad.service.mongodb.IncidenciaServiceMongo;
import com.copernic.projecte2_openroad.service.mongodb.ReservaServiceMongo;
import com.copernic.projecte2_openroad.service.mysql.IncidenciaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/agent")
public class AgentDashboardController {

    // MySQL
    @Autowired
    VehicleServiceSQL vehicleServiceSQL;

    @Autowired
    ReservaServiceSQL reservaServiceSQL;

    @Autowired
    IncidenciaServiceSQL incidenciaServiceSQL;

    // MongoDB

    @Autowired
    ReservaServiceMongo reservaServiceMongo;

    @Autowired
    IncidenciaServiceMongo incidenciaServiceMongo;

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
            model.addAttribute("error", "No se pudo obtener los datos del agente.");
            return "errorPage"; // Cambiar a una página de error adecuada
        }
        Agent agent = (Agent) agentObj;

        // Obtener vehículos en la misma localidad que el agente
        List<Vehicle> vehicles = vehicleServiceSQL.getVehiclesByAgentLocalitat(agent.getLocalitat().getCodiPostalLoc());

        // Filtrar las incidencias relacionadas con los vehículos del agente
        List<Vehicle> finalVehicles = vehicles;
        List<Incidencia> incidencies = incidenciaServiceSQL.llistarIncidencies().stream()
                .filter(incidencia -> finalVehicles.contains(incidencia.getVehicle()))
                .collect(Collectors.toList());

        // Obtener otras entidades necesarias
        List<Reserva> reserves = reservaServiceSQL.llistarReserves();
        List<EstatVehicle> estatsVehicle = Arrays.asList(EstatVehicle.values());
        Collections.sort(estatsVehicle);

        // Obtener marcas y modelos
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());
        List<String> models = vehicleServiceSQL.getAtributsVehicle(Vehicle::getModel, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());

        // Aplicar filtros si están presentes
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

        return "dashboardAgent"; // Nombre de la vista Thymeleaf
    }


    @GetMapping("/vehicle/{matricula}")
    public String detallsVehicle(@PathVariable("matricula") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).orElse(null);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "infoVehiculo";
    }

    @GetMapping("/crear_vehicle")
    public String mostrarFormulariVehicle(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);

        return "crearVehicle";
    }

    @PostMapping("/crear")
    public String crearVehicle(@ModelAttribute Vehicle vehicle,
            @RequestParam("imagen") MultipartFile file, Model model) {
        try {
            // Crear y guardar la imagen
            Imagen image = new Imagen();
            image.setNombre(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setImageData(file.getBytes());

            // Asociar la imagen con el vehículo
            vehicle.setImage(image);

            // Generar la URL de la imagen
            String base64Image = Base64.getEncoder().encodeToString(image.getImageData());
            String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
            vehicle.setImageUrl(imageUrl);

            // Obtenir la localitat de l'agent
            Object agentObj = UserUtils.obtenirDadesUsuariModel(model);
            if (agentObj instanceof Agent) {
                Agent agent = (Agent) agentObj;
                vehicle.setLocalitat(agent.getLocalitat());
            }
            vehicle.setEstatVehicle(EstatVehicle.INACTIU);
            vehicleServiceSQL.guardarVehicle(vehicle);
            return "redirect:/agent/dashboard";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/delete/vehicle/{matricula}")
    public String deleteClient(@PathVariable String matricula) {
        vehicleServiceSQL.eliminarVehiclePerId(matricula);
        return "redirect:/agent/dashboard";
    }

    @GetMapping("/edit/vehicle/{matricula}")
    public String editVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        }
        return "ModificarVehicles"; // Nombre del archivo Thymeleaf
    }

    @GetMapping("/activar/vehicle/{matricula}")
    public String activarVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            vehicle.get().setEstatVehicle(EstatVehicle.ACTIU);
            vehicleServiceSQL.modificarVehicle(vehicle.get());
        }
        return "redirect:/agent/dashboard";
    }

    @GetMapping("/desactivar/vehicle/{matricula}")
    public String desactivarVehicle(@PathVariable String matricula, Model model) {
        Optional<Vehicle> vehicle = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicle.isPresent()) {
            vehicle.get().setEstatVehicle(EstatVehicle.INACTIU);
            vehicleServiceSQL.modificarVehicle(vehicle.get());
        }
        return "redirect:/agent/dashboard";
    }

    @PostMapping("/editVehicle")
    public String guardarCambios(@ModelAttribute Vehicle vehiculo, @RequestParam String matricula, Model model) {
        // Buscar el vehículo que se está editando por su matrícula enviada en el
        // formulario
        Optional<Vehicle> vehiculoExistente = vehicleServiceSQL.findByMatricula(matricula);

        if (vehiculoExistente.isPresent()) {
            Vehicle vehiculoACambiar = vehiculoExistente.get();

            // Actualizar los datos del vehículo
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
            // vehiculoACambiar.setDescVehicle(vehiculo.getDescVehicle());

            // Guardar los cambios
            vehicleServiceSQL.modificarVehicle(vehiculoACambiar);
            return "redirect:/agent/dashboard"; // Redirigir al panel de administración
        } else {
            model.addAttribute("error", "El vehículo no existe o no es válido.");
            return "ModificarVehicles"; // Mostrar la página con el error
        }
    }

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





//vehicleServiceSQL.findByMatricula(incidencia.getVehicle().getMatricula()));


}
