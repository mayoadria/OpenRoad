package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepositorySQL extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findByVehicle(Vehicle vehicle);
    List<Reserva> findByClient_nom(String usuario);
}

