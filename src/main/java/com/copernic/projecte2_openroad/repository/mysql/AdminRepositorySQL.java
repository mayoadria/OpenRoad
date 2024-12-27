package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AdminRepositorySQL extends JpaRepository<Admin, String> {
    Admin findByNomUsuari(String nomUsuari);
}

