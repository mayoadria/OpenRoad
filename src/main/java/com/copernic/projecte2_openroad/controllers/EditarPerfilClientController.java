package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
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
    private ClientServiceSQL clienteService;  // Servicio para manejar operaciones con los datos del cliente.

    @GetMapping("")
    public String mostrarPerfil(Model model, @AuthenticationPrincipal Usuari usuarioLogueado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();  // Obtener el nombre de usuario logueado
            model.addAttribute("nomUsuari", nomUsuari);  // Pasar el nombre de usuario al modelo
            model.addAttribute("isLogged", true);  // Indicar que el usuario está logueado

            // Obtener el cliente logueado desde la base de datos usando el nombre de usuario
            Client cliente = clienteService.obtenerClientePorNombreUsuario(nomUsuari);

            if (cliente != null) {
                model.addAttribute("cliente", cliente);  // Pasar el cliente a la vista
            } else {
                model.addAttribute("error", "Cliente no encontrado");
            }
        } else {
            model.addAttribute("isLogged", false);  // Indicar que el usuario no está logueado
        }
        return "Perfil";  // Nombre del archivo HTML para mostrar el perfil
    }

    // Manejar el formulario de edición de perfil
    @PostMapping("/edit")
    public String guardarCambios(@ModelAttribute Client cliente, @AuthenticationPrincipal Usuari usuarioLogueado, Model model, HttpServletRequest request, HttpServletResponse response) {
        // Comprobar si el usuario está logueado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nombreUsuarioLogueado = authentication.getName();  // Obtener el nombre de usuario logueado

            // Obtener el cliente logueado desde la base de datos
            Client clienteExistente = clienteService.obtenerClientePorNombreUsuario(nombreUsuarioLogueado);

            if (clienteExistente != null) {
                // Actualizar los datos del cliente
                clienteExistente.setNom(cliente.getNom());
                clienteExistente.setCognom1(cliente.getCognom1());
                clienteExistente.setCognom2(cliente.getCognom2());
                clienteExistente.setNumContacte1(cliente.getNumContacte1());
                clienteExistente.setCodiPostal(cliente.getCodiPostal());
                clienteExistente.setAdreca(cliente.getAdreca());
                // Actualizar email y derivar el nuevo nombre de usuario
                String nuevoEmail = cliente.getEmail();
                clienteExistente.setEmail(nuevoEmail);

                // Derivar nuevo nombre de usuario a partir del nuevo email
                String nuevoNombreUsuario = nuevoEmail.split("@")[0];

                // Comprobar si el nombre de usuario ha cambiado
                if (!nuevoNombreUsuario.equals(clienteExistente.getNomUsuari())) {
                    // Validar si el nuevo nombre de usuario está disponible
                    Client clienteConNuevoNombre = clienteService.obtenerClientePorNombreUsuario(nuevoNombreUsuario);
                    if (clienteConNuevoNombre != null) {
                        model.addAttribute("error", "El nombre de usuario derivado del nuevo email ya está en uso.");
                        return "Perfil";  // Mostrar el error si el nombre de usuario ya está en uso
                    }
                    // Si el nombre de usuario es único, actualizarlo
                    clienteExistente.setNomUsuari(nuevoNombreUsuario);
                    new SecurityContextLogoutHandler().logout(request, response, authentication);

                }

                // Guardar los cambios en la base de datos
                clienteService.modificarClient(clienteExistente);
                return "redirect:/";

            } else {
                model.addAttribute("error", "Cliente no encontrado.");
                return "Perfil";  // Redirigir al perfil si el cliente no existe
            }
        } else {
            return "redirect:/login";  // Redirigir a la página de login si no está autenticado
        }
    }





}
