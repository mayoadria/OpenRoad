/**
 * Servei personalitzat per validar i carregar els detalls dels usuaris
 * a partir de la base de dades. Implementa la interfície {@link UserDetailsService}
 * de Spring Security per gestionar l'autenticació.
 */
package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe que valida i carrega els usuaris per a l'autenticació.
 */
@Service
public class ValidadorUsuaris implements UserDetailsService {

    @Autowired
    private UsuariServiceSQL usuariServiceSQL;

    /**
     * Constructor per defecte.
     */
    public ValidadorUsuaris() {
    }

    /**
     * Carrega els detalls d'un usuari a partir del seu nom d'usuari.
     *
     * @param username el nom d'usuari utilitzat per cercar-lo.
     * @return un objecte {@link UserDetails} que conté els detalls de l'usuari.
     * @throws UsernameNotFoundException si l'usuari no es troba o no està activat.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca l'usuari a la base de dades
        Usuari usuari = usuariServiceSQL.findByNomUsuari(username);
        if (usuari == null || !usuari.isEnabled()) {
            throw new UsernameNotFoundException("Usuari no trobat o no activat");
        }
        return usuari;
    }
}
