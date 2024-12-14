package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.enums.Reputacio;
import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mongodb.DocumentServiceMongo;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
            cli.setPermisos(TipusPermis.MOSTRAR_PEPE.toString());
            clientServiceSQL.guardarClient(cli);

            // Processar i guardar imatges a MongoDB
            DocumentClient document = new DocumentClient();
            document.setIdClient(cli.getDni());

            //
            Binary dniImatge = new Binary(dniFile.getBytes());
            Binary carnetImatge = new Binary(carnetFile.getBytes());
            List<Binary> docList = new ArrayList<>();
            docList.add(dniImatge);
            docList.add(carnetImatge);

            document.setClientDoc(docList);

            // Guardar a MongoDB
            documentServiceMongo.guardarDocument(document);

            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Pàgina d'error si falla alguna cosa
        }
    }
}
