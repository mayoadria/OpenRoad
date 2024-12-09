package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class LoginClienteController {

    private final ClientServiceSQL clientServiceSQL;

    public LoginClienteController(ClientServiceSQL clientServiceSQL) {
        this.clientServiceSQL = clientServiceSQL;
    }

    // Método para mostrar la página de login
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Credenciales incorrectas. Por favor, inténtalo de nuevo.");
        }

        if (logout != null) {
            model.addAttribute("logoutMsg", "Has cerrado sesión exitosamente.");
        }

        return "login"; // Devuelve la vista login.html
    }


    // Método para verificar las credenciales del usuario
    @PostMapping("/user")
    public String loginSubmit(@RequestParam("nomUsuari") String username,
                              @RequestParam("contrasenya") String password, Model model) {

        // Verifica las credenciales en la base de datos
        var user = clientServiceSQL.llistarClientPerNomUsuari(username);
// Verifica si el usuario existe y la contraseña es correcta
        if (user == null || !user.getContrasenya().equals(password)) {
            // Si las credenciales son incorrectas, redirige al login con el parámetro de error
            return "redirect:/login?error=true";
        }

            // Si las credenciales son correctas, redirige a la página de inicio (por ejemplo, "index.html")
            return "redirect:/";


    }


}
