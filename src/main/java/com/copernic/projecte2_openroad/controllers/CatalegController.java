package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CatalegController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    // 1. Listar vehículos
    @GetMapping("/cataleg")
    public String listarVehiculos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);
        } else {
            model.addAttribute("isLogged", false);
        }

        List<Vehicle> vehicles = vehicleServiceSQL.listarTodosLosVehiculos();
        model.addAttribute("vehicles", vehicles);
        return "cataleg";
    }

    // Mostrar formulario para crear un vehículo
    @GetMapping("/crear_vehicle")
    public String mostrarFormularioCreacion(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false); // Inicializar variable isLogged

        // Devolvemos el nombre de la vista del formulario (HTML)
        return "crearVehicle";  // Asegúrate de tener una vista llamada crearVehiculo.html
    }

    // Procesar la creación del vehículo (POST)
    @PostMapping("/crear")
    public String crearVehiculo(@ModelAttribute Vehicle vehicle, Model model) {
        try {
            vehicleServiceSQL.guardarVehicle(vehicle);
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el vehicle. Verifique los datos.");
            return "crearVehicle";
        }
        return "redirect:/cataleg";
    }


    // Mostrar detalles de un vehículo
    @GetMapping("vehicle/{matricula1}")
    public String detallsVehicle(@PathVariable("matricula1") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();


        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "infoVehiculo";
    }

    @GetMapping("reserva/{matricula2}")
    public String mostrarPagaReserva(@PathVariable("matricula2") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();


        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "pagaReserva";
    }




}
