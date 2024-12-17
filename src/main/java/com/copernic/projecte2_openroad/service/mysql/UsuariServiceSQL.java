package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.repository.mysql.AdminRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.AgentRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.ClientRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariServiceSQL {

    @Autowired
    private AdminRepositorySQL adminRepository;

    @Autowired
    private ClientRepositorySQL clientRepository;

    @Autowired
    private AgentRepositorySQL agentRepository;

    /**
     * Busca un usuario por su nombre de usuario (nomUsuari) en todos los repositorios.
     */
    public Usuari findByNomUsuari(String nomUsuari) {
        Usuari user = adminRepository.findByNomUsuari(nomUsuari);

        if (user == null) {
            user = clientRepository.findByNomUsuari(nomUsuari);
        }

        if (user == null) {
            user = agentRepository.findByNomUsuari(nomUsuari);
        }

        return user; // Devuelve null si no se encuentra en ningún repositorio
    }

    public void activateUser(String nomUsuari) {
        Client user = clientRepository.findByNomUsuari(nomUsuari);
        user.setEnabled(true);
        clientRepository.save(user);
    }

    /**
     * Guarda un cliente en la base de datos.
     */
    public String guardarClient(Client client) {
        try {
            clientRepository.save(client);
            return "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Client: DNI(" + client.getDni() + "). Excepció: " + e.getMessage();
        }
    }

    public void activateUser(String nomUsuari) {
        Client user = clientRepository.findByNomUsuari(nomUsuari);
        user.setEnabled(true);
        clientRepository.save(user);
    }

    /**
     * Guarda un administrador en la base de datos.
     */
    public String guardarAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
            return "Admin: " + admin.getNom() + " amb DNI(" + admin.getDni() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Admin: DNI(" + admin.getDni() + "). Excepció: " + e.getMessage();
        }
    }

    /**
     * Lista todos los administradores.
     */
    public List<Admin> llistarAdmins() {
        return adminRepository.findAll();
    }

    /**
     * Actualiza los datos de un administrador.
     */
    public void modificarAdmin(Admin admin) {
        adminRepository.save(admin);  // Guarda los cambios en la base de datos
    }


    /**
     * Guarda un agente en la base de datos.
     */
    public String guardarAgent(Agent agent) {
        try {
            agentRepository.save(agent);
            return "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") s'ha creat correctament!";
        } catch (Exception e) {
            return "Error amb Agent: DNI(" + agent.getDni() + "). Excepció: " + e.getMessage();
        }
    }

    /**
     * Lista todos los agentes.
     */
    public List<Agent> llistarAgents() {
        return agentRepository.findAll();
    }
    public List<Client> llistarClient() {
        return clientRepository.findAll();
    }
    /**
     * Actualiza los datos de un agente.
     */
    public void modificarAgent(Agent agent) {
        agentRepository.save(agent);  // Guarda los cambios en la base de datos
    }

    public void modificarClient(Client client) {
        clientRepository.save(client);  // Guarda los cambios en la base de datos
    }

    /**
     * Elimina un agente por su nombre de usuario.
     */
    public String eliminarAgentPerNomUsuari(String nomUsuari) {
        Agent agent = agentRepository.findByNomUsuari(nomUsuari);
        try {
            if (agent != null) {
                agentRepository.delete(agent);
                return "Agent: " + agent.getNom() + " amb DNI(" + agent.getDni() + ") esborrat correctament!";
            } else {
                return "Agent: nomUsuari(" + nomUsuari + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Agent: nomUsuari(" + nomUsuari + "). Excepció: " + e.getMessage();
        }
    }

    public String eliminarClientPerNomUsuari(String nomUsuari) {
        Client client = clientRepository.findByNomUsuari(nomUsuari);
        try {
            if (client != null) {
                clientRepository.delete(client);
                return "Agent: " + client.getNom() + " amb DNI(" + client.getDni() + ") esborrat correctament!";
            } else {
                return "Agent: nomUsuari(" + nomUsuari + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Agent: nomUsuari(" + nomUsuari + "). Excepció: " + e.getMessage();
        }
    }
}
