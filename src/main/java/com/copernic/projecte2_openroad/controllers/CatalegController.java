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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CatalegController {
    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

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

        List<Vehicle> cars = vehicleServiceSQL.listarTodosLosVehiculos();
        model.addAttribute("cars", cars);
        return "cataleg";
    }

    @GetMapping("/CrearVehicle")
    public String mostrarFormularioCreacion(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);
        return "CrearVehicles";
    }

    @PostMapping("/crear")
    public String crearVehiculo(@ModelAttribute Vehicle vehicle) {
        vehicleServiceSQL.guardarVehicle(vehicle);
        return "redirect:/cataleg";
    }
}
