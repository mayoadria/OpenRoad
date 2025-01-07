package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.EstatReserva;
import com.copernic.projecte2_openroad.model.enums.EstatVehicle;
import com.copernic.projecte2_openroad.model.mongodb.Comentari;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.security.UserUtils;
import com.copernic.projecte2_openroad.service.mongodb.ComentarisServiceMongo;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller

public class PagamentController {

    @Autowired
    private UsuariServiceSQL clientServiceSQL;
    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;
    @Autowired
    private ReservaServiceSQL reservaServiceSQL;
    @Autowired
    private ComentarisServiceMongo comentarisServiceMongo;

    private static final Logger logger = LoggerFactory.getLogger(PagamentController.class);
    @GetMapping("/vehicle/{matricula1}")
    public String detallsVehicle(@PathVariable("matricula1") String matricula, Model model) {
        Optional<Vehicle> vehicleOptional = vehicleServiceSQL.findByMatricula(matricula);
        if (vehicleOptional.isEmpty()) {
            model.addAttribute("error", "Vehicle no trobat.");
            return "ErrorPage"; // Página de error específica
        }

        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = null;
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari; // Usuario autenticado como Cliente
        }

        Vehicle vehicle = vehicleOptional.get();

        List<Comentari> comentaris = comentarisServiceMongo.llistarComentariPerMatricula(vehicle.getMatricula());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("comentaris", comentaris);

        Reserva reserva = new Reserva();
        if (client != null) {
            reserva.setClient(client); // Asociar cliente autenticado
        }
        model.addAttribute("reserva", reserva);

        return "Reserva"; // Vista "Reserva" utiliza estos datos
    }

    @PostMapping("/processForm")
    public String processForm(
            @ModelAttribute("reserva") Reserva reserva,
            @RequestParam("preuComplert") Double preuComplert,
            @RequestParam("matricula2") String vehicleMatricula,
            @RequestParam("fechaRecogida") String fechaRecogida,
            @RequestParam("fechaEntrega") String fechaEntrega,
            Model model
    ) {
        try {
            // Parsear las fechas de String a LocalDate
            // Convertir las fechas a LocalDate
            LocalDate fechaInicio = LocalDate.parse(fechaRecogida);
            LocalDate fechaFin = LocalDate.parse(fechaEntrega);


            logger.info("Fecha de recogida: {}", fechaRecogida);
            logger.info("Fecha de entrega: {}", fechaEntrega);
            // Agregar las fechas al modelo para mostrarlas en la vista si es necesario
            model.addAttribute("fechaRecogida", fechaRecogida);
            model.addAttribute("fechaEntrega", fechaEntrega);

            // Validar las fechas
            if (fechaInicio.isAfter(fechaFin)) {
                model.addAttribute("error", "La fecha de recogida no puede ser posterior a la de entrega.");
                return "ErrorPage";
            }

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
            reserva.setFechaRecogida(fechaInicio);
            reserva.setFechaEntrega(fechaFin);
            reserva.setEstatReserva(EstatReserva.PENDENT);
            vehicle.setEstatVehicle(EstatVehicle.RESERVAT);
            // Guardar la reserva
            reservaServiceSQL.guardarReserva(reserva);

            Long idReserva = reserva.getIdReserva();
            return "redirect:/factura/" + idReserva;

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "Reserva";  // Mantener al usuario en la página de reserva en caso de error
        }
    }

}
