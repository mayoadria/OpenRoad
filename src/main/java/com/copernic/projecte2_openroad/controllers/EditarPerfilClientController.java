package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class EditarPerfilClientController {

    @Autowired
    private UsuariServiceSQL usuariServiceSQL;  // Servicio para manejar operaciones con los datos del cliente.

    @GetMapping("")
    public String mostrarPerfil(Model model, @AuthenticationPrincipal Usuari usuarioLogueado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();  // Obtener el nombre de usuario logueado
            model.addAttribute("nomUsuari", nomUsuari);  // Pasar el nombre de usuario al modelo
            model.addAttribute("isLogged", true);  // Indicar que el usuario está logueado

            // Obtener el cliente logueado desde la base de datos usando el nombre de usuario
            Usuari usuari = usuariServiceSQL.findByNomUsuari(nomUsuari);

            if (usuari != null) {
                model.addAttribute("cliente", usuari);  // Pasar el cliente a la vista
            } else {
                model.addAttribute("error", "Cliente no encontrado");
            }
        } else {
            model.addAttribute("isLogged", false);  // Indicar que el usuario no está logueado
        }
        return "Perfil";  // Nombre del archivo HTML para mostrar el perfil
    }

    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Client cliente, @AuthenticationPrincipal Client usuarioLogueado, Model model, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nombreUsuarioLogueado = authentication.getName();  // Obtener el nombre de usuario logueado

            // Obtener el cliente logueado desde la base de datos
            Usuari clienteExistente = usuariServiceSQL.findByNomUsuari(nombreUsuarioLogueado);

            if (clienteExistente != null && clienteExistente instanceof Client) {
                Client clienteACambiar = (Client) clienteExistente;

                // Actualizar los datos del cliente
                clienteACambiar.setNom(cliente.getNom());
                clienteACambiar.setCognom1(cliente.getCognom1());
                clienteACambiar.setCognom2(cliente.getCognom2());
                clienteACambiar.setNumContacte1(cliente.getNumContacte1());
                clienteACambiar.setCodiPostal(cliente.getCodiPostal());
                clienteACambiar.setAdreca(cliente.getAdreca());

                // Actualizar email y derivar el nuevo nombre de usuario
                String nuevoEmail = cliente.getEmail();
                clienteACambiar.setEmail(nuevoEmail);

                // Derivar nuevo nombre de usuario a partir del nuevo email
                String nuevoNombreUsuario = nuevoEmail.split("@")[0];

                // Validar si el nuevo nombre de usuario está disponible y no está en uso
                Usuari clienteConNuevoNombre = usuariServiceSQL.findByNomUsuari(nuevoNombreUsuario);
                if (clienteConNuevoNombre != null && !clienteConNuevoNombre.equals(clienteExistente)) {
                    model.addAttribute("error", "El nombre de usuario derivado del nuevo email ya está en uso.");
                    return "Perfil";  // Mostrar el error si el nombre de usuario ya está en uso
                }

                // Si el nombre de usuario es único, actualizarlo
                clienteACambiar.setNomUsuari(nuevoNombreUsuario);

                // Logout el usuario actual para forzar que inicie sesión nuevamente con el nuevo nombre de usuario
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                // Guardar los cambios en la base de datos
                usuariServiceSQL.modificarClient(clienteACambiar);
                return "redirect:/";
            } else {
                model.addAttribute("error", "Cliente no encontrado o no es de tipo Client.");
                return "Perfil";  // Redirigir al perfil si el cliente no existe o no es del tipo correcto
            }
        } else {
            return "redirect:/login";  // Redirigir a la página de login si no está autenticado
        }
    }






}
