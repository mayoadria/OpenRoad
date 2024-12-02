package com.copernic.projecte2_openroad.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    private ValidadorUsuaris validadorUsuaris;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/static/css/**", "/registre","/").permitAll()  // Permite acceso público a login y recursos estáticos
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otra ruta
                .and()
                .formLogin(form -> form
                        .loginPage("/login")  // Página personalizada de login
                        .loginProcessingUrl("/login")
                        .usernameParameter("nomUsuari") // Nombre personalizado para el campo de usuario
                        .passwordParameter("contrasenya")// URL para procesar el login
                        .defaultSuccessUrl("/", true)  // Redirige a /index después de un login exitoso
                        .failureUrl("/login?error=true")   // Redirige a login con error si las credenciales son incorrectas
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(validadorUsuaris)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usa BCryptPasswordEncoder para cifrar las contraseñas
    }


}

