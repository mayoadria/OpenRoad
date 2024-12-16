package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PagamentController {

//    @Autowired
//    private VehicleServiceSQL vehicleServiceSQL;
//
//    @GetMapping("/reserva/{matricula}")
//    public String mostrarPagaReserva(@PathVariable("matricula") String matricula, Model model) {
//        Optional<Vehicle> vehicleOpt = vehicleServiceSQL.findByMatricula(matricula);
//        if (vehicleOpt.isPresent()) {
//            model.addAttribute("vehicle", vehicleOpt.get());
//            model.addAttribute("isLogged", false); // ajustar según el estado de inicio de sesión
//            return "pagaReserva"; // Retorna la vista para la página de pagaReserva
//        } else {
//            model.addAttribute("error", "Vehículo no encontrado.");
//            return "errorPage"; // Redirige a la página de error personalizada
//        }
//    }
}
