/**
 * Servei que gestiona les operacions CRUD per als usuaris (administradors, clients i agents) emmagatzemats en una base de dades MySQL.
 * Proporciona funcionalitats per crear, modificar, eliminar i llistar usuaris, així com gestionar els seus estats i correus electrònics.
 */
package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.Excepciones.ExcepcionEmailDuplicado;
import com.copernic.projecte2_openroad.Excepciones.ExisteDNI;
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

/**
 * Classe de servei per a la gestió dels usuaris (Admin, Client i Agent) a MySQL.
 */
@Service
public class UsuariServiceSQL {

    @Autowired
    private AdminRepositorySQL adminRepository;

    @Autowired
    private ClientRepositorySQL clientRepository;

    @Autowired
    private AgentRepositorySQL agentRepository;

    /**
     * Cerca un usuari pel seu nom d'usuari en tots els repositoris.
     *
     * @param nomUsuari el nom d'usuari de l'usuari a cercar.
     * @return l'usuari trobat o null si no existeix.
     */
    public Usuari findByNomUsuari(String nomUsuari) {
        Usuari user = adminRepository.findByNomUsuari(nomUsuari);
        if (user == null) {
            user = clientRepository.findByNomUsuari(nomUsuari);
        }
        if (user == null) {
            List<Agent> agents = agentRepository.findByNomUsuari(nomUsuari);
            if (agents.size() > 1) {
                throw new IllegalStateException("Més d'un agent trobat per al nom d'usuari: " + nomUsuari);
            }
            if (!agents.isEmpty()) {
                user = agents.get(0);
            }
        }
        return user;
    }

    /**
     * Guarda un nou client a MySQL.
     *
     * @param client l'entitat del client a guardar.
     */
    public void guardarClient(Client client) {
        if (existeEmail(client.getEmail())) {
            throw new ExcepcionEmailDuplicado("El correu electrònic ja està registrat.");
        }
        if (existeDni( client.getDni() )) {
            throw new ExisteDNI("El dni ja està registrat:");
        }
        clientRepository.save(client);
    }

    /**
     * Activa un client mitjançant el seu nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari del client a activar.
     */
    public void activateUser(String nomUsuari) {
        Client user = clientRepository.findByNomUsuari(nomUsuari);
        user.setEnabled(true);
        clientRepository.save(user);
    }

    /**
     * Guarda un nou administrador a MySQL.
     *
     * @param admin l'entitat de l'administrador a guardar.
     * @return un missatge sobre el resultat de l'operació.
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
     * Llista tots els administradors emmagatzemats a MySQL.
     *
     * @return una llista d'administradors.
     */
    public List<Admin> llistarAdmins() {
        return adminRepository.findAll();
    }

    /**
     * Modifica les dades d'un administrador existent.
     *
     * @param admin l'entitat de l'administrador amb les dades modificades.
     */
    public void modificarAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    /**
     * Verifica si un correu electrònic ja està registrat.
     *
     * @param email el correu electrònic a verificar.
     * @return true si el correu electrònic existeix, false en cas contrari.
     */
    public boolean existeEmail(String email) {
        return agentRepository.existsByEmail(email);
    }

    public boolean existeDni(String dni) {
        return agentRepository.existsByDni(dni);
    }

    /**
     * Guarda un nou agent a MySQL.
     *
     * @param agent l'entitat de l'agent a guardar.
     */
    public void guardarAgent(Agent agent) {
        if (existeEmail(agent.getEmail())) {
            throw new ExcepcionEmailDuplicado("El correu electrònic ja està registrat.");
        }
        if (existeDni( agent.getDni() )) {
            throw new ExisteDNI("El dni ja està registrat:");
        }
        agentRepository.save(agent);
    }

    /**
     * Llista tots els agents emmagatzemats a MySQL.
     *
     * @return una llista d'agents.
     */
    public List<Agent> llistarAgents() {
        return agentRepository.findAll();
    }

    /**
     * Modifica les dades d'un agent existent.
     *
     * @param agent l'entitat de l'agent amb les dades modificades.
     * @param dniAgent el DNI actual de l'agent.
     */
    public void modificarAgent(Agent agent, String dniAgent) {
        if (agent.getDni().equals(dniAgent)) {
            agentRepository.save(agent);
        } else {
            agentRepository.deleteById(dniAgent);
            agentRepository.save(agent);
        }
    }

    /**
     * Elimina un agent pel seu nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari de l'agent a eliminar.
     */
    public void eliminarAgentPerNomUsuari(String nomUsuari) {
        List<Agent> agents = agentRepository.findByNomUsuari(nomUsuari);
        if (agents.size() > 1) {
            throw new IllegalStateException("Més d'un agent trobat per al nom d'usuari: " + nomUsuari);
        } else if (agents.isEmpty()) {
            throw new EntityNotFoundException("No s'ha trobat cap agent per al nom d'usuari: " + nomUsuari);
        }
        agentRepository.delete(agents.get(0));
    }

    /**
     * Llista tots els clients emmagatzemats a MySQL.
     *
     * @return una llista de clients.
     */
    public List<Client> llistarClient() {
        return clientRepository.findAll();
    }

    /**
     * Modifica les dades d'un client existent.
     *
     * @param client l'entitat del client amb les dades modificades.
     */
    public void modificarClient(Client client) {
        clientRepository.save(client);
    }

    /**
     * Elimina un client pel seu nom d'usuari.
     *
     * @param nomUsuari el nom d'usuari del client a eliminar.
     * @return un missatge sobre el resultat de l'operació.
     */
    public String eliminarClientPerNomUsuari(String nomUsuari) {
        Client client = clientRepository.findByNomUsuari(nomUsuari);
        try {
            if (client != null) {
                clientRepository.delete(client);
                return "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") esborrat correctament!";
            } else {
                return "Client: nomUsuari(" + nomUsuari + ") no s'ha trobat a la BD MySQL!";
            }
        } catch (Exception e) {
            return "Error amb Client: nomUsuari(" + nomUsuari + "). Excepció: " + e.getMessage();
        }
    }
}
