package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Reputacio;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registre")
public class RegistroClienteController {

    @Autowired
    ClientServiceSQL clientServiceSQL;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String Registre(Model model) {
        // Pasar los valores del enum al modelo
        model.addAttribute("estados", Reputacio.values());
        return "Registre"; // Nombre del archivo HTML de la vista
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("client") Client cli){
        cli.setContrasenya(passwordEncoder.encode(cli.getContrasenya()));
        String[] part = cli.getEmail().split("@");
        String username = part[0];
        cli.setNomUsuari(username);

        clientServiceSQL.guardarClient(cli);

        return "redirect:/login";

    }
}
