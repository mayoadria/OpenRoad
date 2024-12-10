package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepositorySQL extends JpaRepository<Roles, String> {
    Roles findByName(String name);
}
