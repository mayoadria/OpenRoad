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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatalegController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    // 1. Listar vehículos
    @GetMapping("/cataleg")
    public String listarVehicles(
            @RequestParam(name = "marques", required = false) String marquesFilt,
            @RequestParam(name = "colors",required = false) String colorsFilt,
            @RequestParam(name = "min-dies",required = false) String minDiesFilt,
            @RequestParam(name = "max-dies",required = false) String maxDiesFilt,
            @RequestParam(name = "min-preu",required = false) String minPreuFilt,
            @RequestParam(name = "max-preu",required = false) String maxPreuFilt,
            @RequestParam(name = "min-fian",required = false) String minFianFilt,
            @RequestParam(name = "max-fian",required = false) String maxFianFilt,
            @RequestParam(name = "combustibles",required = false) String combustiblesFilt,
            @RequestParam(name = "portes",required = false) String portesFilt,
            @RequestParam(name = "places",required = false) String placesFilt,
            @RequestParam(name = "caixes-canvis",required = false) String caixesCanvisFilt,
            @RequestParam(name = "marxes",required = false) String marxesFilt,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);
        } else {
            model.addAttribute("isLogged", false);
        }

        List<Vehicle> vehicles = vehicleServiceSQL.listarTodosLosVehiculos();

        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());
        List<Color> colors = vehicleServiceSQL.getAtributsVehicle(Vehicle::getColor, vehicles);
        List<Combustible> combustibles = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCombustible, vehicles);
        int diaLloguerMin = 1;
        int diaLloguerMax = 31;
        List<Double> preuDies = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPreuDia, vehicles);
        int preuDiesMin = 1;
        int preuDiesMax = Collections.max(preuDies).intValue();
        List<Double> fiances = vehicleServiceSQL.getAtributsVehicle(Vehicle::getFianca, vehicles);
        int fiancaMin = 1;
        int fiancaMax = Collections.max(fiances).intValue();
        List<Places> places = Arrays.asList(Places.values());
        Collections.sort(places);
        List<Portes> portes = Arrays.asList(Portes.values());
        Collections.sort(portes);
        List<CaixaCanvis> caixesCanvis = Arrays.asList(CaixaCanvis.values());
        List<Marxes> marxes = Arrays.asList(Marxes.values());
        Collections.sort(marxes);

        if (marquesFilt != null && !marquesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getMarca().equalsIgnoreCase(marquesFilt))
                    .collect(Collectors.toList());
        }   
        if (colorsFilt != null && !colorsFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getColor() == Color.valueOf(colorsFilt))
                    .collect(Collectors.toList());
        } 
        if (minDiesFilt != null && !minDiesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getDiesLloguerMinim() >= Integer.parseInt(minDiesFilt))
                    .collect(Collectors.toList());
        } 
        if (maxDiesFilt != null && !maxDiesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getDiesLloguerMaxim() <= Integer.parseInt(maxDiesFilt))
                    .collect(Collectors.toList());
        } 
        if (minPreuFilt != null && !minPreuFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getPreuDia() >= Integer.parseInt(minPreuFilt))
                    .collect(Collectors.toList());
        } 
        if (maxPreuFilt != null && !maxPreuFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getPreuDia() <= Integer.parseInt(maxPreuFilt))
                    .collect(Collectors.toList());
        }  
        if (minFianFilt != null && !minFianFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getFianca() >= Integer.parseInt(minFianFilt))
                    .collect(Collectors.toList());
        } 
        if (maxFianFilt != null && !maxFianFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getFianca() <= Integer.parseInt(maxFianFilt))
                    .collect(Collectors.toList());
        }  
        if (combustiblesFilt != null && !combustiblesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getCombustible() == Combustible.valueOf(combustiblesFilt))
                    .collect(Collectors.toList());
        } 
        if (portesFilt != null && !portesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getPortes() == Portes.valueOf(portesFilt))
                    .collect(Collectors.toList());
        } 
        if (placesFilt != null && !placesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getPlaces() == Places.valueOf(placesFilt))
                    .collect(Collectors.toList());
        } 
        if (caixesCanvisFilt != null && !caixesCanvisFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getCaixaCanvis() == CaixaCanvis.valueOf(caixesCanvisFilt))
                    .collect(Collectors.toList());
        } 
        if (marxesFilt != null && !marxesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getMarxes() == Marxes.valueOf(marxesFilt))
                    .collect(Collectors.toList());
        } 

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
        model.addAttribute("places", places);
        model.addAttribute("portes", portes);
        model.addAttribute("caixesCanvis", caixesCanvis);
        model.addAttribute("marxes", marxes);

        return "cataleg";
    }

    // Mostrar formulario para crear un vehículo
    @GetMapping("/crear_vehicle")
    public String mostrarFormulariCrear(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);

        model.addAttribute("isLogged", false); // Inicializar variable isLogged

        // Devolvemos el nombre de la vista del formulario (HTML)
        return "crearVehicle"; // Asegúrate de tener una vista llamada crearVehiculo.html
    }

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
