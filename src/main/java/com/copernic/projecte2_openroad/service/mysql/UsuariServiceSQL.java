package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.Excepciones.ExcepcionEmailDuplicado;
import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.repository.mysql.AdminRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.AgentRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.ClientRepositorySQL;
import jakarta.persistence.EntityNotFoundException;
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
        // Primero buscar en el repositorio de administradores
        Usuari user = adminRepository.findByNomUsuari(nomUsuari);

        // Si no se encuentra en administradores, buscar en el repositorio de clientes
        if (user == null) {
            user = clientRepository.findByNomUsuari(nomUsuari);
        }

        // Si no se encuentra en ninguno de los anteriores, buscar en el repositorio de agentes
        if (user == null) {
            List<Agent> agents = agentRepository.findByNomUsuari(nomUsuari);

            // Si hay más de un agente, lanzar excepción
            if (agents.size() > 1) {
                throw new IllegalStateException("Más de un agente encontrado para el nomUsuari: " + nomUsuari);
            }

            // Si se encuentra exactamente un agente, lo devolvemos
            if (agents.size() == 1) {
                user = agents.get(0); // Aquí asignamos el agente al objeto Usuari
            }
        }

        return user; // Devuelve el Usuari encontrado o null si no se encuentra en ninguno de los repositorios
    }



    /**
     * Guarda un cliente en la base de datos.
     */
    public void guardarClient(Client client) {
        if (existeEmail(client.getEmail())) {
            throw new ExcepcionEmailDuplicado("El correu electronic ja està registrat.");
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
    public boolean existeEmail(String email) {
        return agentRepository.existsByEmail(email);
    }

    public void guardarAgent(Agent agent) {
        if (existeEmail(agent.getEmail())) {
            throw new ExcepcionEmailDuplicado("El correo electrónico ya está registrado.");
        }
        agentRepository.save(agent);
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
    public void eliminarAgentPerNomUsuari(String nomUsuari) {
        List<Agent> agents = agentRepository.findByNomUsuari(nomUsuari);
        if (agents.size() > 1) {
            throw new IllegalStateException("Más de un agente encontrado para nomUsuari: " + nomUsuari);
        } else if (agents.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un agente para nomUsuari: " + nomUsuari);
        }
        agentRepository.delete(agents.get(0));
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
