package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mongodb.DocumentServiceMongo;
import jakarta.validation.Valid;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registre")
public class RegistroClienteController {

    @Autowired
    private UsuariServiceSQL clientServiceSQL;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DocumentServiceMongo documentServiceMongo; // Servei per MongoDB

    @GetMapping("")
    public String Registre(Model model) {
        model.addAttribute("paisos", Pais.values());
        model.addAttribute("client", new Client());
        return "Registre";
    }

    @PostMapping("/new")
    public String save(
            @Valid @ModelAttribute("client") Client cli, BindingResult result,
            @RequestParam("dniFile") MultipartFile dniFile,
            @RequestParam("carnetFile") MultipartFile carnetFile,
            Model model
    ) {
        try {
            String validationError = null;

            // Validación de email único
            if (clientServiceSQL.existeEmail(cli.getEmail())) {
                validationError = "El correu electrònic ja està registrat.";
            }

            // Validación de campos vacíos
            if (cli.getNom().isEmpty() || cli.getCognom1().isEmpty() || cli.getEmail().isEmpty() || cli.getContrasenya().isEmpty() || cli.getDni().isEmpty()) {
                validationError = "Tots els camps són obligatoris.";
            }

            // Validación de archivos subidos
            if (dniFile.isEmpty() || carnetFile.isEmpty()) {
                validationError = "És obligatori pujar les fotos del DNI i del carnet.";
            }

            // Enviar error al front-end si algo no está bien
            if (validationError != null) {
                model.addAttribute("popupError", validationError);
                model.addAttribute("paisos", Pais.values());
                return "Registre";
            }

            // Guardar en MySQL
            cli.setContrasenya(passwordEncoder.encode(cli.getContrasenya()));
            String[] part = cli.getEmail().split("@");
            String username = part[0];
            cli.setNomUsuari(username);
            cli.setPermisos(TipusPermis.CLIENT.toString());
            cli.setEnabled(false);
            clientServiceSQL.guardarClient(cli);

            // Guardar en MongoDB
            DocumentClient document = new DocumentClient();
            document.setIdClient(cli.getDni());
            Map<String, Binary> docMap = new HashMap<>();
            docMap.put("dni", new Binary(dniFile.getBytes()));
            docMap.put("carnetConduir", new Binary(carnetFile.getBytes()));
            document.setClientDoc(docMap);
            documentServiceMongo.guardarDocument(document);

            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Página de error si ocurre un problema
        }
    }
}
