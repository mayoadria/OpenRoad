package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.*;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CatalegController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    // 1. Listar vehículos
    @GetMapping("/cataleg")
    public String listarVehiculos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);
        } else {
            model.addAttribute("isLogged", false);
        }

        List<Vehicle> vehicles = vehicleServiceSQL.listarTodosLosVehiculos();
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream().map(String::toLowerCase).collect(Collectors.toList());
        List<Color> colors = vehicleServiceSQL.getAtributsVehicle(Vehicle::getColor, vehicles);
        List<Combustible> combustibles = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCombustible, vehicles);
        int diaLloguerMin = Collections.min(vehicleServiceSQL.getAtributsVehicle(Vehicle::getDiesLloguerMinim, vehicles));
        int diaLloguerMax = Collections.max(vehicleServiceSQL.getAtributsVehicle(Vehicle::getDiesLloguerMaxim, vehicles));
        List<Double> preuDies = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPreuDia, vehicles);
        Double preuDiesMin = Collections.min(preuDies);
        Double preuDiesMax = Collections.max(preuDies);
        List<Double> fiances = vehicleServiceSQL.getAtributsVehicle(Vehicle::getFianca, vehicles);
        Double fiancaMin = Collections.min(fiances);
        Double fiancaMax = Collections.max(fiances);
        List<Places> places = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPlaces, vehicles);
        int placesMin = Collections.min(places).getValor();
        int placesMax = Collections.max(places).getValor();
        List<Portes> portes = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPortes, vehicles);
        int portesMin = Collections.min(portes).getValor();
        int portesMax = Collections.max(portes).getValor();
        List<CaixaCanvis> caixesCanvis = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCaixaCanvis, vehicles);
        List<Marxes> marxes = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarxes, vehicles);
        int marxesMin = Collections.min(marxes).getValor();
        int marxesMax = Collections.max(marxes).getValor();

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("marques", marques);
        model.addAttribute("colors", colors);
        model.addAttribute("combustibles", combustibles);
        model.addAttribute("diaLloguerMin", diaLloguerMin);
        model.addAttribute("diaLloguerMax", diaLloguerMax);
        model.addAttribute("preuDiesMin", preuDiesMin);
        model.addAttribute("preuDiesMax", preuDiesMax);
        model.addAttribute("fiancaMin", fiancaMin);
        model.addAttribute("fiancaMax", fiancaMax);
        model.addAttribute("placesMin", placesMin);
        model.addAttribute("placesMax", placesMax);
        model.addAttribute("portesMin", portesMin);
        model.addAttribute("portesMax", portesMax);
        model.addAttribute("caixesCanvis", caixesCanvis);
        model.addAttribute("marxesMin", marxesMin);
        model.addAttribute("marxesMax", marxesMax);

        return "cataleg";
    }

    // Mostrar formulario para crear un vehículo
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

    // Mostrar detalles de un vehículo
    @GetMapping("vehicle/{matricula1}")
    public String detallsVehicle (@PathVariable("matricula1") String matricula, Model model){
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();


        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "infoVehiculo";
    }


    @GetMapping("reserva/{matricula2}")
    public String mostrarPagaReserva (@PathVariable("matricula2") String matricula, Model model){
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();


        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "pagaReserva";
    }


}
