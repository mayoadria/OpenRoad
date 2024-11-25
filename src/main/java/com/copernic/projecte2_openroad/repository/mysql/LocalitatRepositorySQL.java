package com.copernic.projecte2_openroad.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Localitat;

@Repository
public interface LocalitatRepositorySQL extends JpaRepository<Localitat, String> {

}
