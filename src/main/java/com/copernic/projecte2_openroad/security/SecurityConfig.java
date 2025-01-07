/**
 * Configuració de seguretat per a l'aplicació.
 * Aquesta classe configura la seguretat mitjançant Spring Security, incloent-hi l'autenticació,
 * l'autorització i la configuració de la cadena de filtres de seguretat.
 */
package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ValidadorUsuaris validadorUsuaris;
    private final UsuariServiceSQL usuariServiceSQL;

    /**
     * Constructor que inicialitza els components necessaris per a la seguretat.
     *
     * @param validadorUsuaris  Validador d'usuaris personalitzat.
     * @param usuariServiceSQL  Servei per gestionar els usuaris a MySQL.
     * @param vehicleServiceSQL Servei per gestionar els vehicles (no utilitzat actualment).
     */
    public SecurityConfig(ValidadorUsuaris validadorUsuaris, UsuariServiceSQL usuariServiceSQL, VehicleServiceSQL vehicleServiceSQL) {
        this.validadorUsuaris = validadorUsuaris;
        this.usuariServiceSQL = usuariServiceSQL;
    }

    /**
     * Configura la cadena de filtres de seguretat, incloent-hi les regles d'autorització i autenticació.
     *
     * @param http Configuració de seguretat HTTP.
     * @return la cadena de filtres de seguretat.
     * @throws Exception si es produeix un error en configurar la seguretat.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout", "/css/**", "/images/**", "/cataleg", "/registre/**", "/", "/scripts/**", "/admin/loginAdmin").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(TipusPermis.ADMIN.toString())
                        .requestMatchers("/client/**").hasAnyAuthority(TipusPermis.CLIENT.toString(), TipusPermis.ADMIN.toString(), TipusPermis.AGENT.toString())
                        .requestMatchers("/agent/**").hasAnyAuthority(TipusPermis.CLIENT.toString(), TipusPermis.ADMIN.toString(), TipusPermis.AGENT.toString())
                        .requestMatchers("/reserva/**").hasAnyAuthority(TipusPermis.CLIENT.toString(), TipusPermis.ADMIN.toString(), TipusPermis.AGENT.toString())
                        .requestMatchers("/perfil/**").hasAnyAuthority(TipusPermis.CLIENT.toString(), TipusPermis.ADMIN.toString(), TipusPermis.AGENT.toString())
                        .requestMatchers("/factura/**").hasAnyAuthority(TipusPermis.CLIENT.toString(), TipusPermis.ADMIN.toString(), TipusPermis.AGENT.toString())
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("nomUsuari")
                        .passwordParameter("contrasenya")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .userDetailsService(validadorUsuaris);

        crearAdminSiNoExiste();

        return http.build();
    }

    /**
     * Crea el gestor d'autenticació utilitzant un validador d'usuaris i un codificador de contrasenyes.
     *
     * @param http            Configuració de seguretat HTTP.
     * @param passwordEncoder Codificador de contrasenyes.
     * @return el gestor d'autenticació configurat.
     * @throws Exception si es produeix un error en configurar el gestor d'autenticació.
     */
    @SuppressWarnings("removal")
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(new ValidadorUsuaris())
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    /**
     * Defineix el codificador de contrasenyes utilitzant BCrypt.
     *
     * @return el codificador de contrasenyes.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Crea un administrador per defecte si no existeix a la base de dades.
     */
    private void crearAdminSiNoExiste() {
        Usuari adminExistente = usuariServiceSQL.findByNomUsuari("admin");
        if (adminExistente != null) {
            System.out.println("El administrador ja existeix.");
            return;
        }

        Admin admin = new Admin();
        admin.setContrasenya(passwordEncoder().encode("admin"));
        admin.setDni("12345678A");
        admin.setNom("admin");
        admin.setAdreca("admin");
        admin.setCognom1("admin");
        admin.setEmail("admin@admin.com");
        admin.setNomUsuari("admin");
        admin.setPermisos(TipusPermis.ADMIN.toString());
        admin.setEnabled(true);
        admin.setCodiPostal("00000");
        admin.setNumContacte1("123456789");
        admin.setPais(Pais.ESPANYA);

        String resultat = usuariServiceSQL.guardarAdmin(admin);
        System.out.println(resultat);
    }
}
