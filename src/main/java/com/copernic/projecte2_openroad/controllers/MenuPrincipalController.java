package com.copernic.projecte2_openroad.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MenuPrincipalController {

    @GetMapping("/")
    public String index(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);
        } else {
            model.addAttribute("isLogged", false);
        }
        return "index";
    }
    

    @GetMapping("/menuVehicles")
    public String menuVehicles() {
        return "menuVehicles";
    }
}
