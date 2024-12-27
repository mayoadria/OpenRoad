package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ValidadorUsuaris implements UserDetailsService {

    public ValidadorUsuaris() {

    }
    @Autowired
    UsuariServiceSQL usuariServiceSQL;


    /* */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        Usuari usuari = usuariServiceSQL.findByNomUsuari(username);
        if (usuari == null || !usuari.isEnabled()) {
            throw new UsernameNotFoundException("Usuario no encontrado o no activado");
        }
        return usuari;
    }
}
