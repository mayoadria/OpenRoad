package com.copernic.projecte2_openroad.service.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.repository.mysql.ClientRepositorySQL;

@Service
public class ClientServiceSQL {

    @Autowired
    private ClientRepositorySQL clientRepoSQL;

    // Crear Client.
    public String guardarClient(Client client) {
        try {
            clientRepoSQL.save(client);
            String msg = "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Client: " + client.getNom() + " amb DNI(" + client.getDni() + "). Excepció: " + e.getMessage();
            return msg;
        }        
    }

    // Llistar Client.
    public Client llistarClientPerId(String dni) {
        return clientRepoSQL.findById(dni).get();
    }

    // Llistar tots els Clients.
    public List<Client> llistarClients() {
        return clientRepoSQL.findAll();
    }

    // Modificar Client.
    public String modificarClient(Client client) {
        try {
            if (llistarClientPerId(client.getDni()) != null) {
                clientRepoSQL.save(client);
                String msg = "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") modificat correctament!";
                return msg;
            } else {
                String msg = "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Client: " + client.getNom() + " amb DNI(" + client.getDni() + "). Excepció: " + e.getMessage();
            return msg;
        }
       
    }

    // Eliminar Client.
    public String eliminarClientPerId(String dni) {
        Client client = llistarClientPerId(dni);
        try {
            if (client != null) {
                clientRepoSQL.delete(client);
                String msg = "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Client: DNI(" + dni + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Client: DNI(" + dni + "). Excepció: " + e.getMessage();
            return msg;
        }
        
    }

}
