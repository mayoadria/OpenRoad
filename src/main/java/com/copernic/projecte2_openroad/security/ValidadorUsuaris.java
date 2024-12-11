package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class ValidadorUsuaris implements UserDetailsService {
    private final UsuariServiceSQL usuariServiceSQL;

    @Autowired
    public ValidadorUsuaris(UsuariServiceSQL usuariServiceSQL) {
        this.usuariServiceSQL = usuariServiceSQL;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        Usuari usuari = usuariServiceSQL.findByNomUsuari(username);

        if (usuari == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));// Agregar el rol explicitamente

        return new User(usuari.getNomUsuari(), usuari.getContrasenya(), authorities);
    }
}
