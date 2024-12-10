package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomerDetailsAdmin implements UserDetails {
    private String nomUsuari;
    private String contraseña;
    private Roles role;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomerDetailsAdmin(String username, String password, Roles role) {
        this.nomUsuari = username;
        this.contraseña = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return nomUsuari;
    }

    public Roles getRole() {
        return role;
    }
}
