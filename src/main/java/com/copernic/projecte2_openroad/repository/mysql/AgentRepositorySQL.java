package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepositorySQL extends JpaRepository<Agent, String> {
    Agent findByNomUsuari(String nomUsuari);
}
