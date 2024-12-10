package com.copernic.projecte2_openroad.security;

import com.copernic.projecte2_openroad.model.enums.Rol;
import com.copernic.projecte2_openroad.model.mysql.Admin;
import com.copernic.projecte2_openroad.model.mysql.Roles;
import com.copernic.projecte2_openroad.repository.mysql.AdminRepositorySQL;
import com.copernic.projecte2_openroad.repository.mysql.RolRepositorySQL;
import com.copernic.projecte2_openroad.service.mysql.AdminServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.ClientServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ValidadorAdmin validadorAdmin;
    private final ValidadorUsuaris validadorUsuaris;
    private final AdminServiceSQL adminServiceSQL;
    private final RolRepositorySQL rolRepositorySQL;
    private final ClientServiceSQL clientServiceSQL; // Añadido para validar clientes

    @Autowired
    public SecurityConfig(ValidadorAdmin validadorAdmin, ValidadorUsuaris validadorUsuaris, AdminServiceSQL adminServiceSQL, RolRepositorySQL rolRepositorySQL, ClientServiceSQL clientServiceSQL) {
        this.validadorAdmin = validadorAdmin;
        this.validadorUsuaris = validadorUsuaris;
        this.adminServiceSQL = adminServiceSQL;
        this.rolRepositorySQL = rolRepositorySQL;
        this.clientServiceSQL = clientServiceSQL;
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
                .authorizeRequests()
                .requestMatchers("/login", "/logout", "/css/**", "/images/**", "/registre/**", "/", "/admin/loginAdmin").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/client/**").hasRole("CLIENT") // Autorización para clientes
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/admin/loginAdmin")
                        .loginProcessingUrl("/admin/loginAdmin")
                        .usernameParameter("nomUsuari")
                        .passwordParameter("contrasenya")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureUrl("/admin/login?error=true")
                )

//                .formLogin(form -> form
//                        .loginPage("/client/loginClient")
//                        .loginProcessingUrl("/client/loginClient")
//                        .usernameParameter("nomUsuari")
//                        .passwordParameter("contrasenya")
//                        .defaultSuccessUrl("/client/dashboard", true)
//                        .failureUrl("/client/login?error=true")
//                )
                .userDetailsService(validadorAdmin) // Registro del validador de administradores
                .userDetailsService(validadorUsuaris); // Registro del validador de clientes

        crearRolSiNoExiste();
        crearAdminSiNoExiste();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void crearRolSiNoExiste() {
        String[] nombresRoles = {"ROLE_ADMIN", "ROLE_AGENT", "ROLE_CLIENT"};
        for (String nombreRol : nombresRoles) {
            if (rolRepositorySQL.findByName(nombreRol) == null) {
                Roles rol = new Roles();
                rol.setName(nombreRol);
                rolRepositorySQL.save(rol);
            }
        }
    }

    private void crearAdminSiNoExiste() {
        if (adminServiceSQL.llistarClientPerNomUsuari("admin") == null) {
            Roles rol = rolRepositorySQL.findByName("ROLE_ADMIN");
            if (rol != null) {
                Admin admin = new Admin();
                admin.setNomUsuari("admin");
                admin.setContrasenya(passwordEncoder().encode("admin"));
                admin.setRole(rol);
                adminServiceSQL.crearAdmin(admin);
                System.out.println("Administrador creado automáticamente.");
            } else {
                System.out.println("No se encontró el rol 'ADMIN'.");
            }
        } else {
            System.out.println("El administrador ya existe.");
        }
    }
}

