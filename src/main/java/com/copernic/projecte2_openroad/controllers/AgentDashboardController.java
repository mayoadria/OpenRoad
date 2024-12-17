package com.copernic.projecte2_openroad.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.copernic.projecte2_openroad.model.enums.EstatVehicle;
import com.copernic.projecte2_openroad.model.mysql.Incidencia;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mongodb.IncidenciaServiceMongo;
import com.copernic.projecte2_openroad.service.mongodb.ReservaServiceMongo;
import com.copernic.projecte2_openroad.service.mysql.IncidenciaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/crear_vehicle")
    public String mostrarFormulariVehicle(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "crearVehicle";
    }

    @PostMapping("/crear")
    public String crearVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleServiceSQL.guardarVehicle(vehicle);
        return "redirect:/agent/dashboard";
    }
}
