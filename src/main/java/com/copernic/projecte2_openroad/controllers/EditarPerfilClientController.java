package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Imagen;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

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
    public String guardarCambios(@ModelAttribute Client cliente,
                                 @RequestParam("imagen") MultipartFile file,
                                 Model model,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nombreUsuarioLogueado = authentication.getName();

            // Obtener el cliente logueado desde la base de datos
            Usuari clienteExistente = usuariServiceSQL.findByNomUsuari(nombreUsuarioLogueado);

            if (clienteExistente != null && clienteExistente instanceof Client) {
                Client clienteACambiar = (Client) clienteExistente;

                // Actualizar los datos del cliente
                clienteACambiar.setNom(cliente.getNom());
                clienteACambiar.setCognom1(cliente.getCognom1());
                clienteACambiar.setNumContacte1(cliente.getNumContacte1());
                clienteACambiar.setCodiPostal(cliente.getCodiPostal());
                clienteACambiar.setAdreca(cliente.getAdreca());

                // Actualizar email y derivar el nuevo nombre de usuario
                String nuevoEmail = cliente.getEmail();
                clienteACambiar.setEmail(nuevoEmail);
                String nuevoNombreUsuario = nuevoEmail.split("@")[0];

                // Validar disponibilidad del nuevo nombre de usuario
                Usuari clienteConNuevoNombre = usuariServiceSQL.findByNomUsuari(nuevoNombreUsuario);
                if (clienteConNuevoNombre != null && !clienteConNuevoNombre.equals(clienteExistente)) {
                    model.addAttribute("error", "El nombre de usuario derivado del nuevo email ya está en uso.");
                    return "Perfil";
                }

                // Si el nuevo nombre de usuario es diferente, actualizamos
                boolean nombreUsuarioCambiado = !nuevoNombreUsuario.equals(clienteACambiar.getNomUsuari());
                clienteACambiar.setNomUsuari(nuevoNombreUsuario);

                // Procesar la imagen si se ha subido una nueva
                if (file != null && !file.isEmpty()) {
                    try {
                        // Crear y guardar la nueva imagen
                        Imagen image = new Imagen();
                        image.setNombre(file.getOriginalFilename());
                        image.setType(file.getContentType());
                        image.setImageData(file.getBytes());

                        // Asociar la imagen con el cliente
                        clienteACambiar.setImage(image);

                        // Generar la URL de la imagen
                        String base64Image = Base64.getEncoder().encodeToString(image.getImageData());
                        String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
                        clienteACambiar.setImageUrl(imageUrl);
                    } catch (IOException e) {
                        model.addAttribute("error", "Error al cargar la imagen: " + e.getMessage());
                        return "Perfil";
                    }
                }

                // Guardar los cambios en la base de datos
                usuariServiceSQL.modificarClient(clienteACambiar);

                // Solo cerrar sesión si el nombre de usuario ha cambiado
                if (nombreUsuarioCambiado) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    // Redirigir al inicio para un nuevo login
                    return "redirect:/";
                }

                // Redirigir al perfil si no se cierra sesión
                return "redirect:/perfil";
            } else {
                model.addAttribute("error", "Cliente no encontrado o no es de tipo Client.");
                return "Perfil";
            }
        }

        // Redirigir a la página de login si no está autenticado
        return "redirect:/login";
    }


}
