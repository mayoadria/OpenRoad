package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.EstatReserva;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.repository.mysql.ReservaRepositorySQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/pagaReserva")
public class PagamentController {

    @Autowired
    private ReservaRepositorySQL reservaRepository;

    @Autowired
    private UsuariServiceSQL clientService;

    @Autowired
    private VehicleServiceSQL vehicleService;

    @PostMapping("/efectuarPagament/{matricula3}")
    public String efectuarPagament(
            @PathVariable("matricula3") String matricula,
            @RequestParam("data_inici") String dataInici,
            @RequestParam("data_final") String dataFinal,
             // Recibir como String para mayor control
            Model model) {

        // Obtener cliente actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuarioLogueado = authentication.getName();
        Client client = (Client) clientService.findByNomUsuari(nombreUsuarioLogueado);
        if (client == null) {
            model.addAttribute("error", "No se encontró el cliente. Por favor, inicie sesión.");
            return "pagaReserva";
        }

        // Obtener vehículo
        Vehicle vehicle = vehicleService.findByMatricula(matricula).orElse(null);
        if (vehicle == null) {
            model.addAttribute("error", "No se encontró el vehículo. Por favor, vuelva a intentarlo.");
            return "pagaReserva";
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        try {
            // Convertir y validar fechas
            LocalDate fechaInicio = LocalDate.parse(dataInici);
            LocalDate fechaFinal = LocalDate.parse(dataFinal);

            if (fechaFinal.isBefore(fechaInicio)) {
                model.addAttribute("error", "La fecha final no puede ser anterior a la fecha de inicio.");
                return "pagaReserva";
            }

            if (dataInici == null || dataInici.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
                model.addAttribute("error", "Las fechas de inicio y finalización son obligatorias.");
                return "pagaReserva";
            }

            // Configurar la reserva
            reserva.setDataInici(fechaInicio);
            reserva.setDataFinal(fechaFinal);
            reserva.setEstatReserva(EstatReserva.ACCEPTADA);
            reserva.setClient(client);
            reserva.setVehicle(vehicle);

            // Guardar en la base de datos
            reservaRepository.save(reserva);

        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
            return "pagaReserva";
        }

        // Redirigir a un método o vista para mostrar la factura
        return "redirect:/factura?reservaId=" + reserva.getIdReserva();
    }



    private Double preuTotalInput(Vehicle vehicle) {
        // Lógica para calcular el precio total
        Double preuPerDia = vehicle.getPreuDia();
        Double fianca = vehicle.getFianca();
        int diasReserva = 3; // Ejemplo de días de reserva, reemplazar con lógica real

        if (preuPerDia == null || fianca == null) {
            throw new IllegalArgumentException("Faltan datos del vehículo para calcular el precio total.");
        }

        return (preuPerDia * diasReserva) + fianca;
    }
}