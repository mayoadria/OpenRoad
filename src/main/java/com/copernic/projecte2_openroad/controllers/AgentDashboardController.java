package com.copernic.projecte2_openroad.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.copernic.projecte2_openroad.model.mysql.*;
import com.copernic.projecte2_openroad.security.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);
        } else {
            model.addAttribute("isLogged", false);
        }

        List<Vehicle> vehicles = vehicleServiceSQL.llistarVehicles();
        List<Reserva> reserves = reservaServiceSQL.llistarReserves();
        List<Incidencia> incidencies = incidenciaServiceSQL.llistarIncidencies();

        List<EstatVehicle> estatsVehicle = Arrays.asList(EstatVehicle.values());
        Collections.sort(estatsVehicle);
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());

        List<String> models = vehicleServiceSQL.getAtributsVehicle(Vehicle::getModel, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());

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

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("reserves", reserves);
        model.addAttribute("incidencies", incidencies);

        model.addAttribute("estatsVehicle", estatsVehicle);
        model.addAttribute("marques", marques);
        model.addAttribute("models", models);
        return "dashboardAgent";
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

        Usuari agent = UserUtils.obtenirDadesUsuariModel(model);
        

        return "crearVehicle";
    }

    @PostMapping("/crear")
    public String crearVehicle(@ModelAttribute Vehicle vehicle,
            @RequestParam("imagen") MultipartFile file) {
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
            // vehiculoACambiar.setEstatVehicle(vehiculo.getEstatVehicle());
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
}
