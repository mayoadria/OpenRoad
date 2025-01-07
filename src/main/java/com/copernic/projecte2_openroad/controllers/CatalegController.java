package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.*;
import com.copernic.projecte2_openroad.model.mysql.Imagen;
import com.copernic.projecte2_openroad.model.mysql.Localitat;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.security.UserUtils;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CatalegController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    /**
     * Endpoint principal per al catàleg de vehicles.
     * @param marquesFilt Filtre per la marca dels vehicles.
     * @param colorsFilt Filtre per el color dels vehicles.
     * @param minDiesFilt Filtre per el nombre mínim de dies de lloguer.
     * @param maxDiesFilt Filtre per el nombre màxim de dies de lloguer.
     * @param minPreuFilt Filtre per el preu mínim de lloguer per dia.
     * @param maxPreuFilt Filtre per el preu màxim de lloguer per dia.
     * @param minFianFilt Filtre per la fiança mínima.
     * @param maxFianFilt Filtre per la fiança màxima.
     * @param combustiblesFilt Filtre per el tipus de combustible.
     * @param portesFilt Filtre per el nombre de portes.
     * @param placesFilt Filtre per el nombre de places.
     * @param caixesCanvisFilt Filtre per el tipus de caixa de canvis.
     * @param marxesFilt Filtre per les marxes dels vehicles.
     * @param poblacionsFilt Filtre per la població dels vehicles.
     * @param model Model per passar dades a la vista.
     * @return Nom de la vista a mostrar.
     */
    @GetMapping("/cataleg")
    public String listarVehicles(
            @RequestParam(name = "marques", required = false) String marquesFilt,
            @RequestParam(name = "colors", required = false) String colorsFilt,
            @RequestParam(name = "min-dies", required = false) String minDiesFilt,
            @RequestParam(name = "max-dies", required = false) String maxDiesFilt,
            @RequestParam(name = "min-preu", required = false) String minPreuFilt,
            @RequestParam(name = "max-preu", required = false) String maxPreuFilt,
            @RequestParam(name = "min-fian", required = false) String minFianFilt,
            @RequestParam(name = "max-fian", required = false) String maxFianFilt,
            @RequestParam(name = "combustibles", required = false) String combustiblesFilt,
            @RequestParam(name = "portes", required = false) String portesFilt,
            @RequestParam(name = "places", required = false) String placesFilt,
            @RequestParam(name = "caixa-canvis", required = false) String caixesCanvisFilt,
            @RequestParam(name = "marxes", required = false) String marxesFilt,
            @RequestParam(name = "poblacions", required = false) String poblacionsFilt,
            Model model) {

        /*
         * Obtenir el nom d'usuari i un atribut de si està loguejat o no i
         * passar-ho al model per a canviar les dades del header.
         */
        UserUtils.obtenirDadesUsuariModel(model);

        // Obtenir la llista completa dels vehicles de la base de dades MySQL.
        List<Vehicle> vehicles = vehicleServiceSQL.llistarVehiclesActius(EstatVehicle.ACTIU);

        // Convertir la imatge del vehicle de byte a Image.
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
         * per a tenir rangs de preus, marques/models, etc.
         */
        // Marques
        List<String> marques = vehicleServiceSQL.getAtributsVehicle(Vehicle::getMarca, vehicles).stream()
                .map(String::toLowerCase).collect(Collectors.toList());
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
        // al més alt.
        if (!vehicles.isEmpty()) {
            Double preuDiesMaxDouble = preuDies.stream().max(Double::compare).orElse(0.0);
            preuDiesMax = (int) Math.ceil(preuDiesMaxDouble);
        }
        // Preu del lloguer mínim i màxim
        int fiancaMin = 1; // Mínim per defecte
        int fiancaMax = 99; // Màxim per defecte
        List<Double> fiances = vehicleServiceSQL.getAtributsVehicle(Vehicle::getFianca, vehicles);
        // Modificar el preu màxim obtenint el preu més alt de la llista i arrodonint-lo
        // al més alt.
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
        // Caixa de canvis
        List<CaixaCanvis> caixesCanvis = Arrays.asList(CaixaCanvis.values());
        // Marxes
        List<Marxes> marxes = Arrays.asList(Marxes.values());
        Collections.sort(marxes);

        List<Localitat> localitats = vehicleServiceSQL.getAtributsVehicle(Vehicle::getLocalitat, vehicles);

        List<String> poblacions = Collections.emptyList(); // Llista buida per defecte
        if (localitats != null) {
            poblacions = localitats.stream()
                    .filter(Objects::nonNull) // Filtra possibles elements nuls en la llista
                    .map(Localitat::getPoblacio)
                    .collect(Collectors.toList());
        }

        /*
         * Aplicar els filtres passats per paràmetres a la URL i filtrar
         * la llista de vehicles segons cada atribut dels filtres.
         */
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
                    .filter(v -> v.getCaixaCanvis().equals(caixesCanvisFilt))
                    .collect(Collectors.toList());
        }
        if (marxesFilt != null && !marxesFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getMarxes().equals(marxesFilt))
                    .collect(Collectors.toList());
        }
        if (poblacionsFilt != null && !poblacionsFilt.isEmpty()) {
            vehicles = vehicles.stream()
                    .filter(v -> v.getLocalitat().getPoblacio().equalsIgnoreCase(poblacionsFilt))
                    .collect(Collectors.toList());
        }

        // Passar al model tots els camps dels filtres i la llista de vehicles
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
        model.addAttribute("poblacions", poblacions);

        return "cataleg";
    }

}
