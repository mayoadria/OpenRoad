package com.copernic.projecte2_openroad.service.mysql;

// Java
import java.util.List;

// Spring
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Model
import com.copernic.projecte2_openroad.model.mysql.Localitat;

// Repository
import com.copernic.projecte2_openroad.repository.mysql.LocalitatRepositorySQL;

@Service
public class LocalitatServiceSQL {

    @Autowired
    private LocalitatRepositorySQL localitatRepoSQL;

    // Crear Localitat.
    public String guardarLocalitat(Localitat localitat) {
        try {
            localitatRepoSQL.save(localitat);
            String msg = "Localitat: " + localitat.getPoblacio() + " amb ID(" + localitat.getCodiPostalLoc() + ") s'ha creat correctament!";
            return msg;
        } catch (Exception e) {
            String msg = "Error amb Localitat: ID(" + localitat.getCodiPostalLoc() + "). Excepció: " + e.getMessage();
            return msg;
        }
    }

    // Llistar Localitat.
    public Localitat llistarLocalitatPerId(String id) {
        return localitatRepoSQL.findById(id).get();
    }

    // Llistar totes les Localitats.
    public List<Localitat> llistarLocalitats() {
        return localitatRepoSQL.findAll();
    }

    // Modificar Localitat.
    public void modificarLocalitat(Localitat localitat) {

                localitatRepoSQL.save(localitat);
    }

    // Eliminar Localitat.
    public String eliminarLocalitatPerId(String id) {
        Localitat localitat = llistarLocalitatPerId(id);
        try {
            if (localitat != null) {
                localitatRepoSQL.delete(localitat);
                String msg = "Localitat: " + localitat.getPoblacio() + " amb ID(" + localitat.getCodiPostalLoc() + ") esborrat correctament!";
                return msg;
            } else {
                String msg = "Localitat: ID(" + id + ") no s'ha trobat a la BD MySQL!";
                return msg;
            }
        } catch (Exception e) {
            String msg = "Error amb Localitat: ID(" + id + "). Excepció: " + e.getMessage();
            return msg;
        }
    }
    public Localitat findByNomUsuari(String codiPostalLoc) {
        Localitat user = localitatRepoSQL.findBycodiPostalLoc(codiPostalLoc);
        return user;
    }
}
