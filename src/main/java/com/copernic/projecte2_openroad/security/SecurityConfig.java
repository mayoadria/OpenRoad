package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.enums.*;
import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Usuari;
import com.copernic.projecte2_openroad.model.mysql.Vehicle;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.VehicleServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final VehicleServiceSQL vehicleServiceSQL;

    @Autowired
    public SecurityConfig(ValidadorUsuaris validadorUsuaris, UsuariServiceSQL usuariServiceSQL, VehicleServiceSQL vehicleServiceSQL) {
        this.validadorUsuaris = validadorUsuaris;
        this.usuariServiceSQL = usuariServiceSQL;
        this.vehicleServiceSQL = vehicleServiceSQL;
    }

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
                .requestMatchers("/login", "/logout", "/css/**", "/images/**", "/cataleg","/registre/**", "/", "/scripts/**","/admin/loginAdmin").permitAll()
                .requestMatchers("/admin/**").hasAuthority(TipusPermis.MOSTRAR_DASHBOARDADMIN.toString())
                .requestMatchers("/client/**").hasAnyAuthority(TipusPermis.MODIFICAR_PERFIL.toString())    // Autorización para clientes
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("nomUsuari")
                        .passwordParameter("contrasenya")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .userDetailsService(validadorUsuaris); // Registro del validador de clientes

        crearAdminSiNoExiste();

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(new ValidadorUsuaris())
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private void crearAdminSiNoExiste() {

        // Verificar si ya existe un administrador
        Usuari adminExistente = usuariServiceSQL.findByNomUsuari("admin");
        if (adminExistente != null) {
            System.out.println("El administrador ya existe.");
            return;
        }

        // Crear administrador si no existe
        Admin admin = new Admin();
        admin.setContrasenya(passwordEncoder().encode("admin"));
        admin.setDni("12345678A");
        admin.setNom("admin");
        admin.setAdreca("admin");
        admin.setCognom1("admin");
        admin.setCognom2("admin");
        admin.setEmail("admin@admin.com");
        String[] part = admin.getEmail().split("@");
        String username = part[0];
        admin.setNomUsuari(username);
        admin.setPermisos(TipusPermis.MOSTRAR_DASHBOARDADMIN.toString());
        admin.setEnabled(true);

        // Llama al método correcto de tu servicio para guardar el administrador
        String resultado = usuariServiceSQL.guardarAdmin(admin);
        System.out.println(resultado);
    }



}

