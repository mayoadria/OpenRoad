package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositorySQL extends JpaRepository<Client, String> {
    Client findByNomUsuari(String nomUsuari);
}
