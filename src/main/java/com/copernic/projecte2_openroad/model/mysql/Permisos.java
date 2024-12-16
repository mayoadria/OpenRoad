package com.copernic.projecte2_openroad.model.mysql;

import com.copernic.projecte2_openroad.security.TipusPermis;
import org.springframework.security.core.GrantedAuthority;


public class Permisos implements GrantedAuthority {
    TipusPermis permis;


    public Permisos(TipusPermis permis) {
        this.permis = permis;
    }


    @Override
    public String getAuthority() {
        return permis.toString();
    }

}
