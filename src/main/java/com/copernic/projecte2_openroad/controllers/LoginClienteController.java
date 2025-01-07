package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar el inicio de sesión de los clientes.
 */
@Controller
@RequestMapping("")
public class LoginClienteController {

    private final UsuariServiceSQL usuariServiceSQL;

    /**
     * Constructor del controlador que inyecta el servicio de usuarios.
     *
     * @param usuariServiceSQL el servicio para gestionar usuarios.
     */
    public LoginClienteController(UsuariServiceSQL usuariServiceSQL) {
        this.usuariServiceSQL = usuariServiceSQL;
    }

    /**
     * Muestra la página de inicio de sesión.
     *
     * @param error  indica si hubo un error en el inicio de sesión.
     * @param logout indica si el usuario ha cerrado sesión exitosamente.
     * @param model  el modelo utilizado para pasar datos a la vista.
     * @return el nombre de la vista "login".
     */
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

    /**
     * Procesa la solicitud de inicio de sesión del usuario.
     *
     * @param username el nombre de usuario proporcionado.
     * @param password la contraseña proporcionada.
     * @return una redirección a la página principal si las credenciales son correctas,
     *         o a la página de inicio de sesión con un mensaje de error si son incorrectas.
     */
    @PostMapping("/user")
    public String loginSubmit(@RequestParam("nomUsuari") String username,
                              @RequestParam("contrasenya") String password) {

        // Verifica las credenciales en la base de datos
        var user = usuariServiceSQL.findByNomUsuari(username);

        // Verifica si el usuario existe y la contraseña es correcta
        if (user == null || !user.getContrasenya().equals(password)) {
            // Si las credenciales son incorrectas, redirige al login con el parámetro de error
            return "redirect:/login?error=true";
        }
        return "redirect:/"; // Redirige a la página principal si las credenciales son correctas
    }
}
