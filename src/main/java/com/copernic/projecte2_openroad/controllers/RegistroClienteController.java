package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Reputacio;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import com.copernic.projecte2_openroad.service.mongodb.DocumentServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/registre")
public class RegistroClienteController {

    @Autowired
    private ClientServiceSQL clientServiceSQL;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DocumentServiceMongo documentServiceMongo; // Servei per MongoDB

    @GetMapping("")
    public String Registre(Model model) {
        model.addAttribute("estados", Reputacio.values());
        return "Registre";
    }

    @PostMapping("/new")
    public String save(
            @ModelAttribute("client") Client cli,
            @RequestParam("dniFile") MultipartFile dniFile,
            @RequestParam("carnetFile") MultipartFile carnetFile
    ) {
        try {
            // Processar i guardar dades a MySQL
            cli.setContrasenya(passwordEncoder.encode(cli.getContrasenya()));
            String[] part = cli.getEmail().split("@");
            String username = part[0];
            cli.setNomUsuari(username);

            clientServiceSQL.guardarClient(cli);

            // Processar i guardar imatges a MongoDB
            documentServiceMongo.guardarDocuments(cli.getDni(), dniFile, carnetFile);

            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Pàgina d'error si falla alguna cosa
        }
    }
}
