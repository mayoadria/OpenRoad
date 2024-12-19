package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.*;
import com.copernic.projecte2_openroad.model.mysql.Imagen;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatalegController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    // Endpoint principal per el catàleg.
    @GetMapping("/cataleg")
    public String listarVehicles(@RequestParam(name = "marques", required = false) String marquesFilt, @RequestParam(name = "colors", required = false) String colorsFilt, @RequestParam(name = "min-dies", required = false) String minDiesFilt, @RequestParam(name = "max-dies", required = false) String maxDiesFilt, @RequestParam(name = "min-preu", required = false) String minPreuFilt, @RequestParam(name = "max-preu", required = false) String maxPreuFilt, @RequestParam(name = "min-fian", required = false) String minFianFilt, @RequestParam(name = "max-fian", required = false) String maxFianFilt, @RequestParam(name = "combustibles", required = false) String combustiblesFilt, @RequestParam(name = "portes", required = false) String portesFilt, @RequestParam(name = "places", required = false) String placesFilt, @RequestParam(name = "caixa-canvis", required = false) String caixesCanvisFilt, @RequestParam(name = "marxes", required = false) String marxesFilt, Model model) {
        List<Vehicle> vehicles = vehicleServiceSQL.llistarVehicles();

        // Pasar la imatge del vehicle de byte a Imagen.
        for (Vehicle vehicle : vehicles) {
            if (vehicle != null && vehicle.getImage() != null) {
                Imagen image = vehicle.getImage();
                byte[] imageData = image.getImageData();

                if (imageData != null) {
                    String base64Image = Base64.getEncoder().encodeToString(imageData);

                    String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
                    vehicle.setImageUrl(imageUrl);
                }
            }
        }

        /*
         * Obtenir la llista per a cada filtre dels atributs de cada vehicle
         * per a tenir rangs de preus, marques/models... dels vehicles creats sense
         * repetir
         */
        // Marques
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream().map(String::toLowerCase).collect(Collectors.toList());
        // Colors
        List<Color> colors = vehicleServiceSQL.getAtributsVehicle(Vehicle::getColor, vehicles);
        // Combustibles
        List<Combustible> combustibles = vehicleServiceSQL.getAtributsVehicle(Vehicle::getCombustible, vehicles);
        // Dies de lloguer mínim i màxim
        int diaLloguerMin = 1; // Mínim per defecte
        int diaLloguerMax = 31; // Màxim per defecte
        // Preu del lloguer mínim i màxim
        int preuDiesMin = 1; // Mínim per defecte
        int preuDiesMax = 99; // Màxim per defecte
        List<Double> preuDies = vehicleServiceSQL.getAtributsVehicle(Vehicle::getPreuDia, vehicles);
        // Modificar el preu màxim obtenint el preu més alt de la llista i arrodonint-lo
        // al més al natural més alt.
        if (!vehicles.isEmpty()) {
            Double preuDiesMaxDouble = preuDies.stream().max(Double::compare).orElse(0.0);
            preuDiesMax = (int) Math.ceil(preuDiesMaxDouble);
        }
        // Preu del lloguer mínim i màxim
        int fiancaMin = 1; // Mínim per defecte
        int fiancaMax = 99; // Màxim per defecte
        List<Double> fiances = vehicleServiceSQL.getAtributsVehicle(Vehicle::getFianca, vehicles);
        // Modificar el preu màxim obtenint el preu més alt de la llista i arrodonint-lo
        // al més al natural més alt.
        if (!vehicles.isEmpty()) {
            Double fiancaMaxDouble = fiances.stream().max(Double::compare).orElse(0.0);
            fiancaMax = (int) Math.ceil(fiancaMaxDouble);
        }
        // Places
        List<Places> places = Arrays.asList(Places.values());
        Collections.sort(places);
        // Portes
        List<Portes> portes = Arrays.asList(Portes.values());
        Collections.sort(portes);
        // Caixa canvis
        List<CaixaCanvis> caixesCanvis = Arrays.asList(CaixaCanvis.values());
        // Marxes
        List<Marxes> marxes = Arrays.asList(Marxes.values());
        Collections.sort(marxes);

        /*
         * Aplicar filtres passats per paràmetres per la URL i filtrar
         * la llista de vehicles per a cada atribut dels filtres
         */
        if (marquesFilt != null && !marquesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getMarca().equalsIgnoreCase(marquesFilt)).collect(Collectors.toList());
        }
        if (colorsFilt != null && !colorsFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getColor() == Color.valueOf(colorsFilt)).collect(Collectors.toList());
        }
        if (minDiesFilt != null && !minDiesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getDiesLloguerMinim() >= Integer.parseInt(minDiesFilt)).collect(Collectors.toList());
        }
        if (maxDiesFilt != null && !maxDiesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getDiesLloguerMaxim() <= Integer.parseInt(maxDiesFilt)).collect(Collectors.toList());
        }
        if (minPreuFilt != null && !minPreuFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getPreuDia() >= Integer.parseInt(minPreuFilt)).collect(Collectors.toList());
        }
        if (maxPreuFilt != null && !maxPreuFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getPreuDia() <= Integer.parseInt(maxPreuFilt)).collect(Collectors.toList());
        }
        if (minFianFilt != null && !minFianFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getFianca() >= Integer.parseInt(minFianFilt)).collect(Collectors.toList());
        }
        if (maxFianFilt != null && !maxFianFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getFianca() <= Integer.parseInt(maxFianFilt)).collect(Collectors.toList());
        }
        if (combustiblesFilt != null && !combustiblesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getCombustible() == Combustible.valueOf(combustiblesFilt)).collect(Collectors.toList());
        }
        if (portesFilt != null && !portesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getPortes() == Portes.valueOf(portesFilt)).collect(Collectors.toList());
        }
        if (placesFilt != null && !placesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getPlaces() == Places.valueOf(placesFilt)).collect(Collectors.toList());
        }
        if (caixesCanvisFilt != null && !caixesCanvisFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getCaixaCanvis().equals(caixesCanvisFilt)).collect(Collectors.toList());
        }
        if (marxesFilt != null && !marxesFilt.isEmpty()) {
            vehicles = vehicles.stream().filter(v -> v.getMarxes().equals(marxesFilt)).collect(Collectors.toList());
        }

        // Pasar al model tots els camps dels filtres i la llista dels vehicles
        // (filtrats o no)
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

    @GetMapping("/vehicle/{matricula1}")
    public String detallsVehicle(@PathVariable("matricula1") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).orElse(null);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "infoVehiculo";
    }

    // Endpoint per a reservar un vehicle
    @GetMapping("reserva/{matricula2}")
    public String mostrarPagaReserva(@PathVariable("matricula2") String matricula, Model model) {
        Vehicle vehicle = vehicleServiceSQL.findByMatricula(matricula).get();

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isLogged", false);
        return "pagaReserva";
    }

}
