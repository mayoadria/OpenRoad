/**
 * Servei que gestiona les operacions CRUD per als vehicles emmagatzemats a una base de dades MySQL.
 * Proporciona funcionalitats per crear, llistar, modificar i eliminar vehicles.
 */
package com.copernic.projecte2_openroad.service.mysql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.copernic.projecte2_openroad.model.enums.EstatVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.repository.mysql.VehicleRepositorySQL;

/**
 * Classe de servei per a la gestió dels vehicles a MySQL.
 */
@Service
public class VehicleServiceSQL {

    @Autowired
    private VehicleRepositorySQL vehicleRepoSQL;

    /**
     * Guarda un nou vehicle a MySQL.
     *
     * @param vehicle l'entitat del vehicle a guardar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String guardarVehicle(Vehicle vehicle) {
        try {
            vehicleRepoSQL.save(vehicle);
            return "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
        }
    }

    /**
     * Llista tots els vehicles emmagatzemats a MySQL.
     *
     * @return una llista amb tots els vehicles.
     */
    public boolean existeVehiculo(String matricula) {
        return vehicleRepoSQL.existsByMatricula(matricula);
    }

    // Listar todos los vehículos
    public List<Vehicle> llistarVehicles() {
        return vehicleRepoSQL.findAll();
    }

    /**
     * Llista tots els vehicles que tenen un estat específic.
     *
     * @param estatVehicle l'estat dels vehicles a cercar.
     * @return una llista de vehicles amb l'estat especificat.
     */
    public List<Vehicle> llistarVehiclesActius(EstatVehicle estatVehicle) {
        return vehicleRepoSQL.findByEstatVehicle(estatVehicle);
    }

    /**
     * Cerca vehicles associats a una localitat específica pel seu codi postal.
     *
     * @param codiPostalLoc el codi postal de la localitat.
     * @return una llista de vehicles associats a la localitat especificada.
     */
    public List<Vehicle> getVehiclesByAgentLocalitat(String codiPostalLoc) {
        return vehicleRepoSQL.findByLocalitat_CodiPostalLoc(codiPostalLoc);
    }

    /**
     * Cerca un vehicle a MySQL per la seva matrícula.
     *
     * @param matricula la matrícula del vehicle a cercar.
     * @return un {@link Optional} que conté el vehicle si existeix.
     */
    public Optional<Vehicle> findByMatricula(String matricula) {
        return vehicleRepoSQL.findById(matricula);
    }

    /**
     * Modifica un vehicle existent a MySQL.
     *
     * @param vehicle l'entitat del vehicle a modificar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String modificarVehicle(Vehicle vehicle) {
        try {
            Optional<Vehicle> vehicleExistente = findByMatricula(vehicle.getMatricula());

            if (vehicleExistente.isPresent()) {
                vehicleRepoSQL.save(vehicle);
                return "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") modificat correctament!";
            } else {
                return "Vehicle: ID(" + vehicle.getMatricula() + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
        }
    }

    /**
     * Elimina un vehicle de MySQL pel seu ID (matrícula).
     *
     * @param matricula la matrícula del vehicle a eliminar.
     * @return un missatge informant sobre el resultat de l'operació.
     */
    public String eliminarVehiclePerId(String matricula) {
        Optional<Vehicle> vehicle = findByMatricula(matricula);
        try {
            if (vehicle.isPresent()) {
                vehicleRepoSQL.delete(vehicle.get());
                return "Vehicle: " + vehicle.get().getMarca() + " " + vehicle.get().getModel() + " amb ID(" + matricula + ") esborrat correctament!";
            } else {
                return "Vehicle: ID(" + matricula + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + matricula + "). Excepció: " + e.getMessage();
        }
    }

    /**
     * Obté una llista d'atributs únics de vehicles.
     *
     * @param <T>        el tipus de l'atribut.
     * @param getAtribut una funció que retorna l'atribut d'un vehicle.
     * @param vehicles   la llista de vehicles.
     * @return una llista d'atributs únics.
     */
    public <T> List<T> getAtributsVehicle(Function<Vehicle, T> getAtribut, List<Vehicle> vehicles) {
        Set<T> filtres = new HashSet<>();
        for (Vehicle vehicle : vehicles) {
            filtres.add(getAtribut.apply(vehicle));
        }
        return new ArrayList<>(filtres);
    }
}
