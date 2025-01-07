/**
 * Classe utilitària per gestionar i obtenir les dades de l'usuari autenticat.
 * Aquesta classe proporciona mètodes per afegir informació de l'usuari al model
 * i determinar el seu rol dins del sistema.
 */
package com.copernic.projecte2_openroad.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;

public class UserUtils {

    /**
     * Obtén les dades de l'usuari autenticat i les afegeix al model.
     * Depenent del rol de l'usuari (Admin, Agent o Client), s'assignen valors específics
     * al model per a l'ús posterior en la vista.
     *
     * @param model l'objecte {@link Model} on s'afegiran les dades de l'usuari.
     * @return l'objecte de l'usuari autenticat (Admin, Agent o Client), o null si no hi ha usuari autenticat.
     */
    public static Object obtenirDadesUsuariModel(Model model) {
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
