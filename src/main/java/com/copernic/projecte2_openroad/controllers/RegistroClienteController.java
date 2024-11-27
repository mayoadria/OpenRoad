package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Reputacio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/registre")
public class RegistroClienteController {
    @GetMapping("")
    public String Registre(Model model) {
        // Pasar los valores del enum al modelo
        model.addAttribute("estados", Reputacio.values());
        return "Registre"; // Nombre del archivo HTML de la vista
    }
//
//    @GetMapping("/")
//    public String findAll(Model model){
//        return "MenuInicial";
//    }
}
