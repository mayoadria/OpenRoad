package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.repository.mysql.AdminRepositorySQL;
import com.copernic.projecte2_openroad.service.mysql.AdminServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;

@Service
public class ValidadorAdmin implements UserDetailsService {

    @Autowired
    private AdminRepositorySQL adminRepositorySQL;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepositorySQL.findByNomUsuari(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Agregar el rol explicitamente

        return new org.springframework.security.core.userdetails.User(admin.getNomUsuari(), admin.getContrasenya(), authorities);
    }
}

