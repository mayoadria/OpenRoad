package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

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
            String msg = "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Llistar Vehicle.
    public Vehicle llistarVehiclePerId(String id) {
        return vehicleRepoSQL.findById(id).get();
    }

    // Llistar totes les Vehicles.
    public List<Vehicle> llistarReserves() {
        return vehicleRepoSQL.findAll();
    }

    // Modificar Vehicle.
    public String modificarVehicle(Vehicle vehicle) {
        try {
            if (llistarVehiclePerId(vehicle.getMatricula()) != null) {
                vehicleRepoSQL.save(vehicle);
                String msg = "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Vehicle: ID(" + vehicle.getMatricula() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Vehicle: ID(" + vehicle.getMatricula() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Vehicle.
    public String eliminarVehiclePerId(String id) {
        Vehicle vehicle = llistarVehiclePerId(id);
        try {
            if (vehicle != null) {
                vehicleRepoSQL.delete(vehicle);
                String msg = "Vehicle: " + vehicle.getMarca() + " " + vehicle.getModel() + " amb ID(" + vehicle.getMatricula() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Vehicle: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Vehicle: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
