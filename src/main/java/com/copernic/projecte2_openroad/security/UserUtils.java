package com.copernic.projecte2_openroad.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;

public class UserUtils {

    public static Usuari obtenirDadesUsuariModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {

            Object principal = authentication.getPrincipal();

            if (principal instanceof Admin) {
                Admin admin = (Admin) principal;
                String nomUsuari = admin.getNomUsuari();
                model.addAttribute("nomUsuari", nomUsuari);
                model.addAttribute("isLogged", true);
                model.addAttribute("isAdmin", true);
                model.addAttribute("isAgent", false);
                model.addAttribute("isClient", false);

                return admin;
            } else if (principal instanceof Agent) {
                Agent agent = (Agent) principal;
                String nomUsuari = agent.getNomUsuari();
                model.addAttribute("nomUsuari", nomUsuari);
                model.addAttribute("isLogged", true);
                model.addAttribute("isAdmin", false);
                model.addAttribute("isAgent", true);
                model.addAttribute("isClient", false);

                return agent;
            } else if (principal instanceof Client) {
                Client client = (Client) principal;
                String nomUsuari = client.getNomUsuari();
                model.addAttribute("nomUsuari", nomUsuari);
                model.addAttribute("isLogged", true);
                model.addAttribute("isAdmin", false);
                model.addAttribute("isAgent", false);
                model.addAttribute("isClient", true);

                return client;
            }
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isAgent", false);
            model.addAttribute("isClient", false);
            model.addAttribute("isLogged", false);

            return null;
        }
        return null;
    }
}
