package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Localitat;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.LocalitatRepositorySQL;

/**
 * Servicio encargado de gestionar las localidades en la base de datos.
 * Este servicio proporciona métodos para crear, listar, modificar y eliminar localidades.
 * Utiliza el repositorio {@link LocalitatRepositorySQL} para interactuar con la base de datos y realizar operaciones CRUD sobre las localidades.
 *
 * @see LocalitatRepositorySQL
 * @see Localitat
 */
@Service
public class LocalitatServiceSQL {

    /**
     * Repositorio utilizado para interactuar con la base de datos y gestionar las localidades.
     */
    @Autowired
    private LocalitatRepositorySQL localitatRepoSQL;

    /**
     * Guarda una nueva localidad en la base de datos.
     *
     * Este método recibe una localidad, la guarda en la base de datos y devuelve un mensaje indicando si la creación fue exitosa.
     * Si ocurre un error, el mensaje incluirá los detalles de la excepción.
     *
     * @param localitat La localidad que se desea guardar.
     * @return Un mensaje indicando si la localidad fue creada exitosamente o si ocurrió un error.
     */
    public String guardarLocalitat(Localitat localitat) {
        try {
            localitatRepoSQL.save(localitat);
            String msg = "Localitat: " + localitat.getPoblacio() + " amb ID(" + localitat.getCodiPostalLoc() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Localitat: ID(" + localitat.getCodiPostalLoc() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Obtiene una localidad de la base de datos por su ID.
     *
     * Este método consulta la base de datos para obtener la localidad correspondiente al ID proporcionado.
     *
     * @param id El ID de la localidad que se desea recuperar.
     * @return La localidad correspondiente al ID o {@code null} si no se encuentra.
     */
    public Localitat llistarLocalitatPerId(String id) {
        return localitatRepoSQL.findById(id).get();
    }

    /**
     * Obtiene todas las localidades almacenadas en la base de datos.
     *
     * Este método recupera todas las localidades y las devuelve como una lista.
     *
     * @return Una lista con todas las localidades en la base de datos.
     */
    public List<Localitat> llistarLocalitats() {
        return localitatRepoSQL.findAll();
    }

    /**
     * Modifica una localidad existente en la base de datos.
     *
     * Este método recibe una localidad con los datos actualizados y la guarda de nuevo en la base de datos.
     *
     * @param localitat La localidad con los datos actualizados.
     */
    public void modificarLocalitat(Localitat localitat) {
        localitatRepoSQL.save(localitat);
    }

    /**
     * Elimina una localidad de la base de datos por su ID.
     *
     * Este método recibe el ID de la localidad a eliminar, verifica si existe en la base de datos y la elimina.
     * Devuelve un mensaje indicando si la localidad fue eliminada correctamente o si no se encontró en la base de datos.
     *
     * @param id El ID de la localidad que se desea eliminar.
     * @return Un mensaje indicando si la localidad fue eliminada correctamente o si no se encontró.
     */
    public String eliminarLocalitatPerId(String id) {
        Localitat localitat = llistarLocalitatPerId(id);
        try {
            if (localitat != null) {
                localitatRepoSQL.delete(localitat);
                String msg = "Localitat: " + localitat.getPoblacio() + " amb ID(" + localitat.getCodiPostalLoc() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Localitat: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Localitat: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    /**
     * Busca una localidad por su código postal en la base de datos.
     *
     * Este método consulta la base de datos para encontrar la localidad correspondiente al código postal proporcionado.
     *
     * @param codiPostalLoc El código postal de la localidad que se desea encontrar.
     * @return La localidad correspondiente al código postal proporcionado o {@code null} si no se encuentra.
     */
    public Localitat findByNomUsuari(String codiPostalLoc) {
        return localitatRepoSQL.findBycodiPostalLoc(codiPostalLoc);
    }
}
