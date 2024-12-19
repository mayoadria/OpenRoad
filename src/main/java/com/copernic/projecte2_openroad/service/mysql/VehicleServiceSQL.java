package com.copernic.projecte2_openroad.service.mysql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.repository.mysql.VehicleRepositorySQL;

@Service
public class VehicleServiceSQL {

    @Autowired
    private VehicleRepositorySQL vehicleRepoSQL;

    public String guardarVehicle(Vehicle vehicle) {
        try {
            vehicleRepoSQL.save(vehicle);
            return "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
        }
    }

    public List<Vehicle> listarTodosLosVehiculos() {
        return vehicleRepoSQL.findAll();
    }

    public Optional<Vehicle> findByMatricula(String matricula) {
        return vehicleRepoSQL.findByMatricula(matricula); // Cambio importante
    }

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

    public <T> List<T> getAtributsVehicle(Function<Vehicle, T> getAtribut, List<Vehicle> vehicles) {
        Set<T> filtres = new HashSet<>();
        for (Vehicle vehicle : vehicles) {
            filtres.add(getAtribut.apply(vehicle));
        }
        return new ArrayList<>(filtres);
    }
}
