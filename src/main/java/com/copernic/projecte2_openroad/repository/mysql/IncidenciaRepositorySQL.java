package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Incidencia;

@Repository
public interface IncidenciaRepositorySQL extends JpaRepository<Incidencia, Long>{
}
