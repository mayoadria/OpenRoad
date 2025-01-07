package com.copernic.projecte2_openroad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.security.UserUtils;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador para gestionar las acciones principales del menú, incluyendo la visualización
 * de la página de inicio y el cierre de sesión del usuario.
 */
@Controller
public class MenuPrincipalController {

    @Autowired
    private VehicleServiceSQL vehicleServiceSQL;

    /**
     * Muestra la página principal del sistema con los últimos vehículos disponibles.
     *
     * Este método recupera los datos del usuario actualmente autenticado y los vehículos
     * disponibles en el sistema. Solo muestra los tres vehículos más recientes.
     *
     * @param model el modelo utilizado para pasar los datos a la vista.
     * @return el nombre de la vista "index", que se usa para renderizar la página principal.
     */
    @GetMapping("/")
    public String index(Model model) {

        // Obtiene los datos del usuario autenticado y los pasa al modelo
        UserUtils.obtenirDadesUsuariModel(model);

        // Recupera la lista de todos los vehículos y pasa los tres últimos al modelo
        List<Vehicle> vehicles = vehicleServiceSQL.llistarVehicles();
        model.addAttribute("vehicles", vehicles.subList(Math.max(vehicles.size() - 3, 0), vehicles.size()));

        return "index"; // Devuelve la vista "index"
    }

    /**
     * Cierra la sesión del usuario autenticado y lo redirige a la página principal.
     *
     * Este método maneja la acción de cierre de sesión, invalidando la sesión actual y
     * redirigiendo al usuario de nuevo a la página principal del sistema.
     *
     * @param request la solicitud HTTP actual.
     * @param response la respuesta HTTP actual.
     * @return una redirección a la página principal, "/".
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Obtiene la autenticación actual del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si el usuario está autenticado, cierra la sesión
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/"; // Redirige a la página principal
    }
}

