package com.copernic.projecte2_openroad.service.mongodb;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mongodb.HistoricIncidencia;

// Repository
import com.copernic.projecte2_openroad.repository.mongodb.IncidenciaRepositoryMongo;

@Service
public class IncidenciaServiceMongo {

    @Autowired
    private IncidenciaRepositoryMongo incidenciaRepoMongo;

    // Crear Incidencia.
    public String guardarIncidencia(HistoricIncidencia incidencia) {
        try {
            incidenciaRepoMongo.save(incidencia);
            String msg = "Incidencia: " + incidencia.getTitolInc() + " amb ID(" + incidencia.getIdIncidencia() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Incidencia: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;     
        }
    }

    // Llistar Incidencia.
    public HistoricIncidencia llistarIncidenciaPerId(String id) {
        return incidenciaRepoMongo.findById(id).get();
    }

    // Llistar tots els Incidencias.
    public List<HistoricIncidencia> llistarIncidencias() {
        return incidenciaRepoMongo.findAll();
    }

    // Modificar Incidencia.
    public String modificarIncidencia(HistoricIncidencia incidencia) {
        try {
            if (llistarIncidenciaPerId(incidencia.getTitolInc()) != null) {
                incidenciaRepoMongo.save(incidencia);
                String msg = "Incidencia: " + incidencia.getTitolInc() + " amb ID(" + incidencia.getIdIncidencia() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Incidencia: ID(" + incidencia.getIdIncidencia() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidencia: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Incidencia.
    public String eliminarIncidenciaPerId(String id) {
        HistoricIncidencia Incidencia = llistarIncidenciaPerId(id);
        try {
            if (Incidencia != null) {
                incidenciaRepoMongo.delete(Incidencia);
                String msg = "Incidencia: " + Incidencia.getTitolInc() + " amb ID(" + Incidencia.getIdIncidencia() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Incidencia: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidencia: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
