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
            // Verificar si el email ya existe
            if (clientServiceSQL.existeEmail(cli.getEmail())) {
                result.rejectValue("email", "error.cli", "El correo electrónico ya está registrado");
            }
            // Processar i guardar dades a MySQL
            cli.setContrasenya(passwordEncoder.encode(cli.getContrasenya()));
            String[] part = cli.getEmail().split("@");
            String username = part[0];
            cli.setNomUsuari(username);
            cli.setPermisos(TipusPermis.CLIENT.toString());
            cli.setEnabled(false);
            if (result.hasErrors()) {
                model.addAttribute("paisos", Pais.values());
                return "Registre";
            }else {
                clientServiceSQL.guardarClient(cli);

                // Processar i guardar imatges a MongoDB
                DocumentClient document = new DocumentClient();
                document.setIdClient(cli.getDni());

                // Crear un mapa amb noms descriptius per les imatges
                Map<String, Binary> docMap = new HashMap<>();
                if (!dniFile.isEmpty()) {
                    docMap.put("dni", new Binary(dniFile.getBytes()));
                }
                if (!carnetFile.isEmpty()) {
                    docMap.put("carnetConduir", new Binary(carnetFile.getBytes()));
                }

                document.setClientDoc(docMap);

                // Guardar a MongoDB
                documentServiceMongo.guardarDocument(document);

                return "redirect:/login";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Pàgina d'error si falla alguna cosa
        }
    }
}
