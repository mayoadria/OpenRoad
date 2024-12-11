package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.CaixaCanvis;
import com.copernic.projecte2_openroad.model.enums.Marxes;
import com.copernic.projecte2_openroad.model.enums.Places;
import com.copernic.projecte2_openroad.model.enums.Portes;
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
import java.util.stream.Collectors;

@Controller
public class CatalegController {
    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    // 1. Listar vehículos
    @GetMapping("/cataleg")
    public String listarVehicles(Model model) {

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
        List<String> colors = vehicleServiceSQL.getAtributsVehicle(Vehicle::getColor, vehicles).stream().map(String::toLowerCase).collect(Collectors.toList());
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream().map(String::toLowerCase).collect(Collectors.toList());
        List<String> combustibles = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCombustible, vehicles).stream().map(String::toLowerCase).collect(Collectors.toList());
        List<Integer> diesLloguerMin = vehicleServiceSQL.getAtributsVehicle(Vehicle::getDiesLloguerMinim, vehicles);
        List<Integer> diesLloguerMax = vehicleServiceSQL.getAtributsVehicle(Vehicle::getDiesLloguerMaxim, vehicles);
        List<Double> preuDies = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPreuDia, vehicles);
        List<Double> fiances = vehicleServiceSQL.getAtributsVehicle(Vehicle::getFianca, vehicles);
        List<Places> places = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPlaces, vehicles);
        List<Portes> portes = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPortes, vehicles);
        List<CaixaCanvis> caixesCanvis = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCaixaCanvis, vehicles);
        List<Marxes> marxes = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarxes, vehicles);


        model.addAttribute("vehicles", vehicles);
        model.addAttribute("colors", colors);
        model.addAttribute("marques", marques);
        model.addAttribute("combustibles", combustibles);
        model.addAttribute("diesLloguerMin", diesLloguerMin);
        model.addAttribute("diesLloguerMax", diesLloguerMax);
        model.addAttribute("preuDies", preuDies);
        model.addAttribute("fiances", fiances);
        model.addAttribute("places", places);
        model.addAttribute("portes", portes);
        model.addAttribute("caixesCanvis", caixesCanvis);
        model.addAttribute("marxes", marxes);

        return "cataleg";
    }

    @GetMapping("/crear_vehicle")
    public String mostrarFormulariCrear(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);

        model.addAttribute("isLogged", false); // Inicializar variable isLogged

        // Devolvemos el nombre de la vista del formulario (HTML)
        return "crearVehicle";  // Asegúrate de tener una vista llamada crearVehiculo.html
    }

    /*
    @GetMapping("/CrearVehicle")
    public String mostrarFormularioCreacion(Model model) {
    Vehicle vehicle = new Vehicle();
    model.addAttribute("vehicle", vehicle);
    model.addAttribute("isLogged", false); // Inicializar variable isLogged
    return "CrearVehicles";
    }
*/

    // 3. Procesar la creación del vehículo (POST)
    @PostMapping("/crear") // Confirmamos que la ruta sea consistente
    public String crearVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleServiceSQL.guardarVehicle(vehicle);
        return "redirect:/cataleg";
    }

    @GetMapping("vehicle/{matricula}")
    public String detallsVehicle(@PathVariable("matricula") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();

        
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "infoVehiculo";
    }

}
