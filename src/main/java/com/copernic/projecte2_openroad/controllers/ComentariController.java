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

/**
 * Controlador per gestionar les operacions relacionades amb els comentaris dels vehicles.
 */
@Controller
public class ComentariController {

    @Autowired
    private ComentarisServiceMongo comentarisServiceMongo;

    /**
     * Mostra el formulari per afegir un comentari a un vehicle.
     *
     * @param matricula la matrícula del vehicle per al qual es vol afegir un comentari.
     * @param model     l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return el nom de la vista del formulari de comentari.
     */
    @GetMapping("/comentariForm/{matricula}")
    public String comentariForm(@PathVariable String matricula, Model model) {
        UserUtils.obtenirDadesUsuariModel(model);
        Comentari comentari = new Comentari();
        model.addAttribute("comentari", comentari);
        model.addAttribute("matricula", matricula);

        return "comentariForm";
    }

    /**
     * Crea un nou comentari i el guarda a la base de dades.
     *
     * @param comentari l'objecte {@link Comentari} amb les dades del comentari.
     * @param matricula la matrícula del vehicle associat al comentari.
     * @param model     l'objecte {@link Model} utilitzat per passar dades a la vista.
     * @return una redirecció a la pàgina del vehicle associat.
     */
    @PostMapping("/crearComentari")
    public String crearComentari(@ModelAttribute Comentari comentari, @RequestParam String matricula, Model model) {
        Object dadesUsuari = UserUtils.obtenirDadesUsuariModel(model);

        Client client = new Client();
        if (dadesUsuari instanceof Client) {
            client = (Client) dadesUsuari;
        }

        comentari.setNomClient(client.getNom());
        comentari.setCognom1Client(client.getCognom1());
        comentari.setNomUsuariClient(client.getNomUsuari());
        comentari.setMatriculaVehicle(matricula);
        LocalDate data = LocalDate.now();
        comentari.setDataComentari(data);
        comentari.setLike(0);
        comentari.setDisLike(0);

        comentarisServiceMongo.guardarComentari(comentari);

        return "redirect:/vehicle/" + matricula;
    }

    /**
     * Modifica els likes i dislikes d'un comentari existent.
     *
     * @param idComentari l'identificador del comentari a modificar.
     * @param matricula   la matrícula del vehicle associat al comentari.
     * @param likes       el nou valor de likes.
     * @param dislikes    el nou valor de dislikes.
     * @return una redirecció a la pàgina del vehicle associat.
     */
    @PostMapping("/modificarLikes")
    public String modificarLikes(
            @RequestParam String idComentari,
            @RequestParam String matricula,
            @RequestParam int likes,
            @RequestParam int dislikes) {

        Comentari comentari = comentarisServiceMongo.llistarComentariPerId(idComentari);

        comentari.setLike(likes);
        comentari.setDisLike(dislikes);

        comentarisServiceMongo.modificarComentari(comentari);

        return "redirect:/vehicle/" + matricula;
    }
}
