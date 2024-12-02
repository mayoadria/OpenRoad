package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 1. Listar vehículos
    @GetMapping("/cataleg")
    public String listarVehiculos(Model model) {
        // Recuperamos la lista de vehículos desde el servicio
        List<Vehicle> cars = vehicleServiceSQL.listarTodosLosVehiculos();

        // Añadimos la lista de vehículos al modelo
        model.addAttribute("cars", cars);

        // Devolvemos el nombre de la vista donde se mostrarán los vehículos (HTML)
        return "cataleg";  // Asegúrate de tener una vista llamada menuVehicles.html
    }

    // 2. Mostrar formulario de creación de vehículo (GET)
    @GetMapping("/CrearVehicle") // Cambié la URL a "/CrearVehicle" para evitar mayúsculas
    public String mostrarFormularioCreacion(Model model) {
        // Creamos un objeto Vehicle vacío para mostrarlo en el formulario
        Vehicle vehicle = new Vehicle();

        // Añadimos el objeto vehicle al modelo
        model.addAttribute("vehicle", vehicle);

        // Devolvemos el nombre de la vista del formulario (HTML)
        return "CrearVehicles";  // Asegúrate de tener una vista llamada crearVehiculo.html
    }

    // 3. Procesar la creación del vehículo (POST)
    @PostMapping("/crear") // Confirmamos que la ruta sea consistente
    public String crearVehiculo(@ModelAttribute Vehicle vehicle) {
        // Guardamos el vehículo en la base de datos a través del servicio
        vehicleServiceSQL.guardarVehicle(vehicle);

        // Después de crear el vehículo, redirigimos a la lista de vehículos
        return "redirect:/menuVehicles";  // Redirige a la lista de vehículos
    }
}
