package com.copernic.projecte2_openroad.service.mysql;

import java.util.ArrayList;
import java.util.HashSet;
// Java
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Vehicle;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.VehicleRepositorySQL;

@Service
public class VehicleServiceSQL {

    @Autowired
    private VehicleRepositorySQL vehicleRepoSQL;

    // Crear Vehicle.
    public String guardarVehicle(Vehicle vehicle) {
        try {
            vehicleRepoSQL.save(vehicle);
            return "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
        }
    }

    // Listar todos los vehículos
    public List<Vehicle> listarTodosLosVehiculos() {
        return vehicleRepoSQL.findAll();
    }

    // Buscar un vehículo por matrícula
    public Optional<Vehicle> findByMatricula(String matricula) {
        return vehicleRepoSQL.findById(matricula); // Asumiendo que la matrícula es la PK
    }

    // Modificar Vehicle.
    public String modificarVehicle(Vehicle vehicle) {
        try {
            // Verificar si el vehículo existe
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

    // Eliminar Vehicle por ID
    public String eliminarVehiclePerId(String id) {
        Optional<Vehicle> vehicle = findByMatricula(id);
        try {
            if (vehicle.isPresent()) {
                vehicleRepoSQL.delete(vehicle.get());
                return "Vehicle: " + vehicle.get().getMarca() + " " + vehicle.get().getModel() + " amb ID(" + id + ") esborrat correctament!";
            } else {
                return "Vehicle: ID(" + id + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + id + "). Excepció: " + e.getMessage();
        }
    }

    // Obtenir llista de atributs dels Vehicles
    public <T> List<T> getAtributsVehicle(Function<Vehicle, T> getAtribut, List<Vehicle> vehicles) {
        Set<T> filtres = new HashSet<>();
        for (Vehicle vehicle : vehicles) {
            filtres.add(getAtribut.apply(vehicle));
        }
        return new ArrayList<>(filtres);
    }
}
