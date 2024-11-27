package com.copernic.projecte2_openroad.service.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.repository.mysql.AgentRepositorySQL;

@Service
public class AgentServiceSQL {

    @Autowired
    private AgentRepositorySQL agentRepoSQL;

    // Crear Agent.
    public String guardarAgent(Agent agent) {
        try {
            agentRepoSQL.save(agent);
            String msg = "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + "). Excepció: " + e.getMessage();
            return msg;        }
    }

    // Llistar Agent.
    public Agent llistarAgentPerId(String dni) {
        return agentRepoSQL.findById(dni).get();
    }

    // Llistar tots els Agents.
    public List<Agent> llistarAgents() {
        return agentRepoSQL.findAll();
    }

    // Modificar Agent.
    public String modificarAgent(Agent agent) {
        try {
            if (llistarAgentPerId(agent.getDni()) != null) {
                agentRepoSQL.save(agent);
                String msg = "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Eliminar Agent.
    public String eliminarAgentPerId(String dni) {
        Agent agent = llistarAgentPerId(dni);
        try {
            if (agent != null) {
                agentRepoSQL.delete(agent);
                String msg = "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Agent: DNI(" + dni + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Agent: DNI(" + dni + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
}
