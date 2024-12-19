package com.copernic.projecte2_openroad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacturaController {

    @GetMapping("/factura")
    public String factura() {
        return "factura";
    }

    @PostMapping("/factura")
    public String generaFactura(
            @RequestParam("data_inici") String dataInici,
            @RequestParam("data_final") String dataFinal,
            @RequestParam("preu_complet") Double preuComplet,
            Model model) {

        // Agregar lógica para procesar la factura
        model.addAttribute("dataInici", dataInici);
        model.addAttribute("dataFinal", dataFinal);
        model.addAttribute("preuComplet", preuComplet);

        // Retornar la vista de factura
        return "factura";
    }
}
