/**
 * Servei que gestiona les operacions CRUD per als comentaris emmagatzemats en una col·lecció MongoDB.
 * Proporciona funcionalitats per crear, llistar, modificar i eliminar comentaris.
 */
package com.copernic.projecte2_openroad.service.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mongodb.Comentari;
import com.copernic.projecte2_openroad.repository.mongodb.ComentariRepositoryMongo;

/**
 * Classe de servei per a la gestió dels comentaris a MongoDB.
 */
@Service
public class ComentarisServiceMongo {

    @Autowired
    private ComentariRepositoryMongo comentariRepoMongo;

    /**
     * Crea un nou comentari i l'emmagatzema a MongoDB.
     *
     * @param comentari l'entitat del comentari a crear.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String guardarComentari(Comentari comentari) {
        try {
            comentariRepoMongo.save(comentari);
            String msg = "Comentari: " + comentari.getTitolComent() + " amb ID(" + comentari.getIdComentari()
                    + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Comentari: ID(" + comentari.getIdComentari() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Cerca un comentari per ID.
     *
     * @param id l'ID del comentari a cercar.
     * @return l'entitat del comentari si existeix.
     */
    public Comentari llistarComentariPerId(String id) {
        return comentariRepoMongo.findById(id).orElse(null);
    }

    /**
     * Cerca una llista de comentaris associats a una matrícula específica de vehicle.
     *
     * @param matricula la matrícula del vehicle.
     * @return una llista de comentaris associats a la matrícula especificada.
     */
    public List<Comentari> llistarComentariPerMatricula(String matricula) {
        return comentariRepoMongo.findComentariByMatriculaVehicle(matricula);
    }

    /**
     * Llista tots els comentaris emmagatzemats.
     *
     * @return una llista amb tots els comentaris.
     */
    public List<Comentari> llistarComentaris() {
        return comentariRepoMongo.findAll();
    }

    /**
     * Modifica un comentari existent.
     *
     * @param comentari l'entitat del comentari a modificar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String modificarComentari(Comentari comentari) {
        try {
            if (llistarComentariPerId(comentari.getIdComentari()) != null) {
                comentariRepoMongo.save(comentari);
                String msg = "Comentari: " + comentari.getTitolComent() + " amb ID(" + comentari.getTitolComent()
                        + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Comentari: ID(" + comentari.getIdComentari() + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Comentari: ID(" + comentari.getTitolComent() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Elimina un comentari existent a partir del seu ID.
     *
     * @param id l'ID del comentari a eliminar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String eliminarComentariPerId(String id) {
        Comentari comentari = llistarComentariPerId(id);
        try {
            if (comentari != null) {
                comentariRepoMongo.delete(comentari);
                String msg = "Comentari: " + comentari.getTitolComent() + " amb ID(" + comentari.getTitolComent()
                        + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Comentari: ID(" + id + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Comentari: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
