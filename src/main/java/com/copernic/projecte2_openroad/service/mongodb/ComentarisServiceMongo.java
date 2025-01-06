package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.Comentari;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.ComentariRepositoryMongo;

@Service
public class ComentarisServiceMongo {

    @Autowired
    private ComentariRepositoryMongo comentariRepoMongo;

    // Crear Comentari.
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

    // Llistar Comentari.
    public Comentari llistarComentariPerId(String id) {
        return comentariRepoMongo.findById(id).get();
    }

    // Llistar Comentari per Matricula.
    public List<Comentari> llistarComentariPerMatricula(String matricula) {
        return comentariRepoMongo.findComentariByMatriculaVehicle(matricula);
    }

    // Llistar tots els Comentaris.
    public List<Comentari> llistarComentaris() {
        return comentariRepoMongo.findAll();
    }

    // Modificar Comentari.
    public String modificarComentari(Comentari comentari) {
        try {
            if (llistarComentariPerId(comentari.getIdComentari()) != null) {
                comentariRepoMongo.save(comentari);
                String msg = "Comentari: " + comentari.getTitolComent() + " amb ID(" + comentari.getTitolComent()
                        + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Comentari: ID(" + comentari.getIdComentari() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Comentari: ID(" + comentari.getTitolComent() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Comentari.
    public String eliminarComentariPerId(String id) {
        Comentari Comentari = llistarComentariPerId(id);
        try {
            if (Comentari != null) {
                comentariRepoMongo.delete(Comentari);
                String msg = "Comentari: " + Comentari.getTitolComent() + " amb ID(" + Comentari.getTitolComent()
                        + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Comentari: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Comentari: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
