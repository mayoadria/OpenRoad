package com.copernic.projecte2_openroad.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomerDetails implements UserDetails {

    private String nomUsuari;
    private String contraseña;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public CustomerDetails(String username, String password) {
        this.nomUsuari = username;
        this.contraseña = password;

    }

    // Métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contraseña   ;
    }

    @Override
    public String getUsername() {
        return nomUsuari;
    }


}
