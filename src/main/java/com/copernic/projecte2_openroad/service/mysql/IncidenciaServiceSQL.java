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

/**
 * Servicio encargado de gestionar las incidencias en la base de datos.
 * Este servicio proporciona métodos para crear, listar, modificar y eliminar incidencias.
 * Utiliza el repositorio {@link IncidenciaRepositorySQL} para interactuar con la base de datos y realizar operaciones CRUD sobre las incidencias.
 *
 * @see IncidenciaRepositorySQL
 * @see Incidencia
 */
@Service
public class IncidenciaServiceSQL {

    /**
     * Repositorio utilizado para interactuar con la base de datos y gestionar las incidencias.
     */
    @Autowired
    private IncidenciaRepositorySQL incidenciaRepoSQL;

    /**
     * Guarda una nueva incidencia en la base de datos.
     *
     * Este método recibe una incidencia, la guarda en la base de datos y devuelve un mensaje indicando si la creación fue exitosa.
     * Si ocurre un error, el mensaje incluirá los detalles de la excepción.
     *
     * @param incidencia La incidencia que se desea guardar.
     * @return Un mensaje indicando si la incidencia fue creada exitosamente o si ocurrió un error.
     */
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

    /**
     * Obtiene una incidencia de la base de datos por su ID.
     *
     * Este método consulta la base de datos para obtener la incidencia correspondiente al ID proporcionado.
     *
     * @param id El ID de la incidencia que se desea recuperar.
     * @return La incidencia correspondiente al ID o {@code null} si no se encuentra.
     */
    public Incidencia llistarIncidenciaPerId(Long id) {
        return incidenciaRepoSQL.findById(id).get();
    }

    /**
     * Obtiene todas las incidencias almacenadas en la base de datos.
     *
     * Este método recupera todas las incidencias y las devuelve como una lista.
     *
     * @return Una lista con todas las incidencias en la base de datos.
     */
    public List<Incidencia> llistarIncidencies() {
        return incidenciaRepoSQL.findAll();
    }

    /**
     * Modifica una incidencia existente en la base de datos.
     *
     * Este método recibe una incidencia con los datos actualizados, verifica si la incidencia existe
     * en la base de datos, y si es así, la guarda de nuevo. Devuelve un mensaje indicando si la modificación
     * fue exitosa o si la incidencia no se encontró.
     *
     * @param incidencia La incidencia con los datos actualizados.
     * @return Un mensaje indicando si la incidencia fue modificada correctamente o si no se encontró.
     */
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

    /**
     * Elimina una incidencia de la base de datos por su ID.
     *
     * Este método recibe el ID de la incidencia a eliminar, verifica si existe en la base de datos y la elimina.
     * Devuelve un mensaje indicando si la incidencia fue eliminada correctamente o si no se encontró en la base de datos.
     *
     * @param id El ID de la incidencia que se desea eliminar.
     * @return Un mensaje indicando si la incidencia fue eliminada correctamente o si no se encontró.
     */
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
