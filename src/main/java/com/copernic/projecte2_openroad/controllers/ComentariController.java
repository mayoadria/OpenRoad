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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComentariController {

    @Autowired
    private ComentarisServiceMongo comentarisServiceMongo;

    // Endpoint per al formulari de comentari per un vehicle
    @GetMapping("/comentariForm/{matricula}")
    public String comentariForm(@PathVariable String matricula, Model model) {

        // Obtenir les dades de l'usuari i passar-les al model
        UserUtils.obtenirDadesUsuariModel(model);

        Comentari comentari = new Comentari();
        model.addAttribute("comentari", comentari);
        model.addAttribute("matricula", matricula);

        return "comentariForm"; // Retorna la vista del formulari
    }

    // Endpoint per crear un nou comentari
    @PostMapping("/crearComentari")
    public String crearComentari(@ModelAttribute Comentari comentari, @RequestParam String matricula, Model model) {

        // Obtenir les dades de l'usuari per associar-les al comentari
        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = new Client();
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari;
        }

        // Assignar les dades del client al comentari
        comentari.setNomClient(client.getNom());
        comentari.setCognom1Client(client.getCognom1());
        comentari.setNomUsuariClient(client.getNomUsuari());
        comentari.setMatriculaVehicle(matricula);
        LocalDate data = LocalDate.now();
        comentari.setDataComentari(data);
        comentari.setLike(0); // Inicialitzar likes
        comentari.setDisLike(0); // Inicialitzar dislikes

        // Guardar el comentari al servei MongoDB
        comentarisServiceMongo.guardarComentari(comentari);

        return "redirect:/vehicle/" + matricula; // Redirigir al vehicle amb la matrícula corresponent
    }

    // Endpoint per modificar els likes i dislikes d'un comentari
    @PostMapping("/modificarLikes")
    public String modificarLikes(
            @RequestParam String idComentari,
            @RequestParam String matricula,
            @RequestParam int likes,
            @RequestParam int dislikes) {

        // Obtenir el comentari per ID
        Comentari comentari = comentarisServiceMongo.llistarComentariPerId(idComentari);

        // Modificar els likes i dislikes del comentari
        comentari.setLike(likes);
        comentari.setDisLike(dislikes);

        // Actualitzar el comentari al servei MongoDB
        comentarisServiceMongo.modificarComentari(comentari);

        return "redirect:/vehicle/" + matricula; // Redirigir al vehicle amb la matrícula corresponent
    }
}
