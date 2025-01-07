package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Imagen;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * Controlador per gestionar la funcionalitat d'edició del perfil d'un client.
 */
@Controller
@RequestMapping("/perfil")
public class EditarPerfilClientController {

    @Autowired
    private UsuariServiceSQL usuariServiceSQL;

    /**
     * Mostra la pàgina del perfil del client loguejat.
     *
     * @param model           l'objecte {@link Model} per passar dades a la vista.
     * @param usuarioLogueado l'usuari actualment autenticat.
     * @return el nom de la vista del perfil.
     */
    @GetMapping("")
    public String mostrarPerfil(Model model, @AuthenticationPrincipal Usuari usuarioLogueado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nomUsuari = authentication.getName();
            model.addAttribute("nomUsuari", nomUsuari);
            model.addAttribute("isLogged", true);

            Usuari usuari = usuariServiceSQL.findByNomUsuari(nomUsuari);

            if (usuari != null) {
                if (usuari instanceof Client) {
                    model.addAttribute("cliente", usuari);
                } else {
                    model.addAttribute("error", "Usuario no es de tipo Client.");
                    return "Perfil";
                }
            } else {
                model.addAttribute("error", "Cliente no encontrado");
            }
        } else {
            model.addAttribute("isLogged", false);
        }
        return "Perfil";
    }

    /**
     * Guarda els canvis realitzats al perfil del client.
     *
     * @param cliente   l'objecte {@link Client} amb les dades actualitzades.
     * @param result    l'objecte {@link BindingResult} per manejar errors de validació.
     * @param file      la imatge carregada per a l'actualització del perfil.
     * @param model     l'objecte {@link Model} per passar dades a la vista.
     * @param request   l'objecte {@link HttpServletRequest} per manejar la sessió.
     * @param response  l'objecte {@link HttpServletResponse} per manejar la resposta HTTP.
     * @return una redirecció a la vista del perfil o al login si cal tornar a autenticar-se.
     */
    @PostMapping("/edit")
    public String guardarCambios(@Valid @ModelAttribute Client cliente,
                                 BindingResult result,
                                 @RequestParam("imagen") MultipartFile file,
                                 Model model,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String nombreUsuarioLogueado = authentication.getName();

            Usuari clienteExistente = usuariServiceSQL.findByNomUsuari(nombreUsuarioLogueado);

            if (clienteExistente != null && clienteExistente instanceof Client) {
                Client clienteACambiar = (Client) clienteExistente;

                clienteACambiar.setNom(cliente.getNom());
                clienteACambiar.setCognom1(cliente.getCognom1());
                clienteACambiar.setNumContacte1(cliente.getNumContacte1());
                clienteACambiar.setCodiPostal(cliente.getCodiPostal());
                clienteACambiar.setAdreca(cliente.getAdreca());

                String nuevoEmail = cliente.getEmail();
                clienteACambiar.setEmail(nuevoEmail);
                String nuevoNombreUsuario = nuevoEmail.split("@")[0];

                Usuari clienteConNuevoNombre = usuariServiceSQL.findByNomUsuari(nuevoNombreUsuario);
                if (clienteConNuevoNombre != null && !clienteConNuevoNombre.equals(clienteExistente)) {
                    model.addAttribute("error", "El nombre de usuario derivado del nuevo email ya está en uso.");
                    return "Perfil";
                }

                boolean nombreUsuarioCambiado = !nuevoNombreUsuario.equals(clienteACambiar.getNomUsuari());
                clienteACambiar.setNomUsuari(nuevoNombreUsuario);

                if (file != null && !file.isEmpty()) {
                    try {
                        Imagen image = new Imagen();
                        image.setNombre(file.getOriginalFilename());
                        image.setType(file.getContentType());
                        image.setImageData(file.getBytes());

                        clienteACambiar.setImage(image);

                        String base64Image = Base64.getEncoder().encodeToString(image.getImageData());
                        String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
                        clienteACambiar.setImageUrl(imageUrl);
                    } catch (IOException e) {
                        model.addAttribute("error", "Error al cargar la imagen: " + e.getMessage());
                        return "Perfil";
                    }
                }

                if (result.hasErrors()) {
                    model.addAttribute("isLogged", true);
                    model.addAttribute("cliente", cliente);
                    return "Perfil";
                } else {
                    usuariServiceSQL.modificarClient(clienteACambiar);

                    if (nombreUsuarioCambiado) {
                        new SecurityContextLogoutHandler().logout(request, response, authentication);
                        return "redirect:/";
                    }

                    return "redirect:/perfil";
                }
            } else {
                model.addAttribute("error", "Cliente no encontrado o no es de tipo Client.");
                return "Perfil";
            }
        }

        return "redirect:/login";
    }
}
