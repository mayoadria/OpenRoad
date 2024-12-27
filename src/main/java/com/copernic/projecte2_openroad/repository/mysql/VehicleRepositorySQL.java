package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepositorySQL extends JpaRepository<Vehicle, String>{
    List<Vehicle> findByLocalitat_CodiPostalLoc(String codiPostalLoc);
}
