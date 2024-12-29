package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;

import java.util.List;
import com.copernic.projecte2_openroad.model.enums.EstatVehicle;


@Repository
public interface VehicleRepositorySQL extends JpaRepository<Vehicle, String>{
    List<Vehicle> findByLocalitat_CodiPostalLoc(String codiPostalLoc);
    List<Vehicle> findByEstatVehicle(EstatVehicle estatVehicle);
}
