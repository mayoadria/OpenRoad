/**
 * Servei que gestiona les operacions CRUD per als registres de logs emmagatzemats en una col·lecció MongoDB.
 * Proporciona funcionalitats per crear, llistar, modificar i eliminar logs.
 */
package com.copernic.projecte2_openroad.service.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mongodb.LogsServer;
import com.copernic.projecte2_openroad.repository.mongodb.LogServerRepositoryMongo;

/**
 * Classe de servei per a la gestió dels logs del servidor a MongoDB.
 */
@Service
public class LogServerServiceMongo {

    @Autowired
    private LogServerRepositoryMongo logRepoMongo;

    /**
     * Guarda un nou registre de log a MongoDB.
     *
     * @param log l'entitat del log a guardar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String guardarLog(LogsServer log) {
        try {
            logRepoMongo.save(log);
            String msg = "Log: " + log.getType() + " amb ID(" + log.getIdLog() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Log: ID(" + log.getIdLog() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Cerca un registre de log a MongoDB pel seu ID.
     *
     * @param id l'ID del log a cercar.
     * @return l'entitat del log si existeix.
     */
    public LogsServer llistarLogPerId(String id) {
        return logRepoMongo.findById(id).orElse(null);
    }

    /**
     * Llista tots els registres de logs emmagatzemats a MongoDB.
     *
     * @return una llista amb tots els logs.
     */
    public List<LogsServer> llistarLogs() {
        return logRepoMongo.findAll();
    }

    /**
     * Modifica un registre de log existent a MongoDB.
     *
     * @param log l'entitat del log a modificar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String modificarLog(LogsServer log) {
        try {
            if (llistarLogPerId(log.getIdLog()) != null) {
                logRepoMongo.save(log);
                String msg = "Log: " + log.getType() + " amb ID(" + log.getIdLog() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Log: ID(" + log.getIdLog() + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Log: ID(" + log.getIdLog() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Elimina un registre de log a MongoDB pel seu ID.
     *
     * @param id l'ID del log a eliminar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String eliminarLogPerId(String id) {
        LogsServer log = llistarLogPerId(id);
        try {
            if (log != null) {
                logRepoMongo.delete(log);
                String msg = "Log: " + log.getType() + " amb ID(" + log.getIdLog() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Log: ID(" + id + ") no s'ha trobat a la BD MongoDB!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Log: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
