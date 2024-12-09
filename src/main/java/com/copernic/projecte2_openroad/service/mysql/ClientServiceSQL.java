package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Client;

// Repository
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
            String msg = "Error amb Client: DNI(" + client.getDni() + "). Excepció: " + e.getMessage();
            return msg;  
        }        
    }

    // Llistar Client.
    public Client llistarClientPerId(String id) {
        return clientRepoSQL.findById(id).get();
    }
    public Client obtenerClientePorNombreUsuario(String username) {
        return clientRepoSQL.findByNomUsuari(username); // Busca al cliente por nombre de usuario
    }
    public Client llistarClientPerNomUsuari(String nomUsuari) {
        return clientRepoSQL.findByNomUsuari(nomUsuari);}

    // Llistar tots els Clients.
    public List<Client> llistarClients() {
        return clientRepoSQL.findAll();
    }

    // Actualizar al cliente
    public void modificarClient(Client cliente) {
        clientRepoSQL.save(cliente);  // Guarda los cambios en la base de datos
    }

    // Eliminar Client.
    public String eliminarClientPerId(String id) {
        Client client = llistarClientPerId(id);
        try {
            if (client != null) {
                clientRepoSQL.delete(client);
                String msg = "Client: " + client.getNom() + " amb DNI(" + client.getDni() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Client: DNI(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Client: DNI(" + id + "). Excepció: " + e.getMessage();
            return msg;  
        }
    }



}
