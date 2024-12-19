package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PagamentController {

    @Autowired
    private UsuariServiceSQL clientServiceSQL;
    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;
    @Autowired
    private ReservaServiceSQL reservaServiceSQL;

    @GetMapping("/vehicle/{matricula1}")
    public String detallsVehicle(@PathVariable("matricula1") String matricula, Model model) {
        Optional<Vehicle> vehicleOptional = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicleOptional.isEmpty()) {
            model.addAttribute("error", "Vehicle no trobat.");
            return "ErrorPage"; // Cambiar a una página de error específica.
        }

        // Añadir vehículo al modelo
        Vehicle vehicle = vehicleOptional.get();
        model.addAttribute("vehicle", vehicle);

        // Inicializar reserva y cliente
        Reserva reserva = new Reserva();
        reserva.setClient(new Client()); // Evita errores al acceder a client.nom, etc.
        model.addAttribute("reserva", reserva);

        model.addAttribute("isLogged", false); // Si necesitas controlar la sesión
        return "Reserva";
    }

    @PostMapping("/processForm")
    public String processForm(
            @ModelAttribute("reserva") Reserva reserva,
            @RequestParam("preuComplert") Double preuComplert,
            @RequestParam("matricula2") String vehicleMatricula,
            Model model
    ) {
        try {
            System.out.println("Valor de preuComplert recibido: " + preuComplert);
            // Validar el precio
            if (preuComplert == null || preuComplert <= 0) {
                throw new IllegalArgumentException("El precio ingresado no es válido.");
            }

            // Obtener el cliente logueado
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuari client = clientServiceSQL.findByNomUsuari(username);
            if (client == null) {
                throw new RuntimeException("Cliente no encontrado.");
            }

            // Verificar el vehículo
            Vehicle vehicle = vehicleServiceSQL.findByMatricula(vehicleMatricula)
                    .orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

            // Crear la reserva
            reserva.setPreuComplert(preuComplert);
            reserva.setClient((Client) client);
            reserva.setVehicle(vehicle);

            // Guardar la reserva
            reservaServiceSQL.guardarReserva(reserva);

            return "redirect:/factura";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "Reserva";  // Mantener al usuario en la página de reserva.
        }
    }
}
