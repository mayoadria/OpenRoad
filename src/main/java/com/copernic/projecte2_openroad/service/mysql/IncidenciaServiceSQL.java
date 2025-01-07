package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Incidencia;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.IncidenciaRepositorySQL;

@Service
public class IncidenciaServiceSQL {

    @Autowired
    private IncidenciaRepositorySQL incidenciaRepoSQL;

    // Crear Incidència.
    public String guardarIncidencia(Incidencia incidencia) {
        try {
            incidenciaRepoSQL.save(incidencia);
            String msg = "Incidència: " + incidencia.getTitol() + " amb ID(" + incidencia.getIdIncidencia() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Llistar Incidència.
    public Incidencia llistarIncidenciaPerId(Long id) {
        return incidenciaRepoSQL.findById(id).get();
    }

    // Llistar totes les Incidències.
    public List<Incidencia> llistarIncidencies() {
        return incidenciaRepoSQL.findAll();
    }

    // Modificar Incidència.
    public String modificarIncidencia(Incidencia incidencia) {
        try {
            if (llistarIncidenciaPerId(incidencia.getIdIncidencia()) != null) {
                incidenciaRepoSQL.save(incidencia);
                String msg = "Incidència: " + incidencia.getTitol() + " amb ID(" + incidencia.getIdIncidencia() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Incidència: ID(" + incidencia.getIdIncidencia() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + incidencia.getIdIncidencia() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Incidència.
    public String eliminarIncidenciaPerId(Long id) {
        Incidencia incidencia = llistarIncidenciaPerId(id);
        try {
            if (incidencia != null) {
                incidenciaRepoSQL.delete(incidencia);
                String msg = "Incidència: " + incidencia.getTitol() + " amb ID(" + incidencia.getIdIncidencia() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Incidència: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Incidència: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
