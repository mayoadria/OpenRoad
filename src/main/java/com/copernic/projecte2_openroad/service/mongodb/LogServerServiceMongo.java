package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.LogsServer;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.LogServerRepositoryMongo;

@Service
public class LogServerServiceMongo {

    @Autowired
    private LogServerRepositoryMongo logRepoMongo;

    // Crear Log.
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

    // Llistar Log.
    public LogsServer llistarLogPerId(String id) {
        return logRepoMongo.findById(id).get();
    }

    // Llistar tots els Logs.
    public List<LogsServer> llistarLogs() {
        return logRepoMongo.findAll();
    }

    // Modificar Log.
    public String modificarLog(LogsServer Log) {
        try {
            if (llistarLogPerId(Log.getIdLog()) != null) {
                logRepoMongo.save(Log);
                String msg = "Log: " + Log.getType() + " amb ID(" + Log.getIdLog() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Log: ID(" + Log.getIdLog() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Log: ID(" + Log.getIdLog() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Log.
    public String eliminarLogPerId(String id) {
        LogsServer Log = llistarLogPerId(id);
        try {
            if (Log != null) {
                logRepoMongo.delete(Log);
                String msg = "Log: " + Log.getType() + " amb ID(" + Log.getIdLog() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Log: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Log: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
