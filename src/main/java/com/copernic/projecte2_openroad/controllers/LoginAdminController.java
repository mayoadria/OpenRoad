package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.service.mysql.AdminServiceSQL;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class LoginAdminController {

    private final AdminServiceSQL adminServiceSQL;
    private final PasswordEncoder passwordEncoder;

    public LoginAdminController(AdminServiceSQL adminServiceSQL, PasswordEncoder passwordEncoder) {
        this.adminServiceSQL = adminServiceSQL;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/loginAdmin")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Credenciales incorrectas. Por favor, inténtalo de nuevo.");
        }
        return "loginAdmin";
    }


    @PostMapping("/user")
    public String loginSubmit(@RequestParam("nomUsuari") String username,
                              @RequestParam("contrasenya") String password,
                              Model model) {
        // Busca al usuario por nombre
        var user = adminServiceSQL.llistarClientPerNomUsuari(username);
        if (user == null || !passwordEncoder.matches(password, user.getContrasenya())) {
            // Si las credenciales son incorrectas
            return "redirect:/admin/login?error=true";
        }
        // Si el login es exitoso
        return "redirect:/admin/dashboard";
    }
}
