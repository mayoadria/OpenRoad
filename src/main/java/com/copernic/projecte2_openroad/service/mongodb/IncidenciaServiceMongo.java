/**
 * Servei que gestiona les operacions CRUD per a les incidències emmagatzemades en una col·lecció MongoDB.
 * Proporciona funcionalitats per crear, llistar, modificar i eliminar incidències.
 */
package com.copernic.projecte2_openroad.service.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mongodb.HistoricIncidencia;
import com.copernic.projecte2_openroad.repository.mongodb.IncidenciaRepositoryMongo;

/**
 * Classe de servei per a la gestió de les incidències a MongoDB.
 */
@Service
public class IncidenciaServiceMongo {

    @Autowired
    private IncidenciaRepositoryMongo incidenciaRepoMongo;

    /**
     * Guarda una nova incidència a MongoDB.
     *
     * @param incidencia l'entitat de la incidència a guardar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String guardarIncidencia(HistoricIncidencia incidencia) {
        try {
            incidenciaRepoMongo.save(incidencia);
            String msg = "Incidència: " + incidencia.getTitolInc() + " amb ID(" + incidencia.getIdIncidencia() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Cerca una incidència a MongoDB pel seu ID.
     *
     * @param id l'ID de la incidència a cercar.
     * @return l'entitat de la incidència si existeix.
     */
    public HistoricIncidencia llistarIncidenciaPerId(String id) {
        return incidenciaRepoMongo.findById(id).orElse(null);
    }

    /**
     * Llista totes les incidències emmagatzemades a MongoDB.
     *
     * @return una llista amb totes les incidències.
     */
    public List<HistoricIncidencia> llistarIncidencias() {
        return incidenciaRepoMongo.findAll();
    }

    /**
     * Modifica una incidència existent a MongoDB.
     *
     * @param incidencia l'entitat de la incidència a modificar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String modificarIncidencia(HistoricIncidencia incidencia) {
        try {
            if (llistarIncidenciaPerId(incidencia.getIdIncidencia()) != null) {
                incidenciaRepoMongo.save(incidencia);
                String msg = "Incidència: " + incidencia.getTitolInc() + " amb ID(" + incidencia.getIdIncidencia() + ") modificada correctament!";
                return msg;
            } else {
                String msg = "Incidència: ID(" + incidencia.getIdIncidencia() + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Elimina una incidència a MongoDB pel seu ID.
     *
     * @param id l'ID de la incidència a eliminar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String eliminarIncidenciaPerId(String id) {
        HistoricIncidencia incidencia = llistarIncidenciaPerId(id);
        try {
            if (incidencia != null) {
                incidenciaRepoMongo.delete(incidencia);
                String msg = "Incidència: " + incidencia.getTitolInc() + " amb ID(" + incidencia.getIdIncidencia() + ") esborrada correctament!";
                return msg;
            } else {
                String msg = "Incidència: ID(" + id + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
