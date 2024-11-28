package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ValidadorUsuaris implements UserDetailsService {
    private final ClientServiceSQL clientService;

    @Autowired
    public ValidadorUsuaris(ClientServiceSQL clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String nomUsuari) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        Client client = clientService.findByNomUsuari(nomUsuari)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nomUsuari));

        // Aquí, el CustomUserDetails encapsula el usuario y la contraseña, además de los roles (si se aplican)
        return new CustomerDetails(
                client.getNomUsuari(),  // El nombre de usuario
                client.getContrasenya() // La contraseña

        );
    }
}
