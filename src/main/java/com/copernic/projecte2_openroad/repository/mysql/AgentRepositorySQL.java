package com.copernic.projecte2_openroad.repository.mysql;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentRepositorySQL extends JpaRepository<Agent, String> {
    List<Agent> findByNomUsuari(String nomUsuari);

}
