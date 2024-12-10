package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.repository.mysql.AdminRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.ClientRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceSQL {
    @Autowired
    private AdminRepositorySQL adminRepositorySQL;
    public Admin llistarClientPerNomUsuari(String nomUsuari) {
        return adminRepositorySQL.findByNomUsuari(nomUsuari);}

    public Admin crearAdmin(Admin admin) {
        return adminRepositorySQL.save(admin);
    }
}
