package com.copernic.projecte2_openroad.repository.mysql;

// Repository JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mysql.Client;

@Repository
public interface ClientRepositorySQL extends JpaRepository<Client, String>{
}
