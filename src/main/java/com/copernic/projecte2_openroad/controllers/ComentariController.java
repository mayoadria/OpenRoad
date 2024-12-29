package com.copernic.projecte2_openroad.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.copernic.projecte2_openroad.model.mongodb.Comentari;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.security.UserUtils;
import com.copernic.projecte2_openroad.service.mongodb.ComentarisServiceMongo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComentariController {

    @Autowired
    private ComentarisServiceMongo comentarisServiceMongo;

    @GetMapping("/comentariForm")
    public String comentariForm(Model model) {

        UserUtils.obtenirDadesUsuariModel(model);

        Comentari comentari = new Comentari();
        model.addAttribute("comentari", comentari);

        return "comentariForm";
    }

    @PostMapping("/crearComentari")
    public String crearComentari(@ModelAttribute Comentari comentari, Model model) {

        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = new Client();
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari;
        }

        comentari.setNomClient(client.getNom());
        comentari.setCognom1Client(client.getCognom1());
        comentari.setNomUsuariClient(client.getNomUsuari());
        comentari.setMatriculaVehicle("12345ABC");
        LocalDate data = LocalDate.now();
        comentari.setDataComentari(data);
        comentari.setLike(0);
        comentari.setDisLike(0);

        comentarisServiceMongo.guardarComentari(comentari);

        return "redirect:/comentariForm";
    }

}
