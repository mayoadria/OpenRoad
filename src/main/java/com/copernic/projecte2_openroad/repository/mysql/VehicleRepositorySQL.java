package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepositorySQL extends JpaRepository<Vehicle, String> {

    // Método para buscar un vehículo por matrícula
    Optional<Vehicle> findByMatricula(String matricula);
}
